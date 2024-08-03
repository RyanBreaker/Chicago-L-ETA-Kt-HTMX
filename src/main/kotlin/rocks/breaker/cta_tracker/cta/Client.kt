package rocks.breaker.cta_tracker.cta

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Client {
    private const val URL = "https://lapi.transitchicago.com/api/1.0/ttarrivals.aspx"

    // TODO: error on startup if missing?
    private val apiKey = System.getenv("CTA_API_KEY")!!

    private val client = HttpClient(CIO) {
        install(HttpCache)
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getEtas(mapId: String): Result<List<Arrival>, HttpResponse> {
        val response = client.get(URL) {
            url {
                parameters.append("key", apiKey)
                parameters.append("outputType", "json")
                parameters.append("mapid", mapId)
            }
        }

        if (!response.status.isSuccess()) {
            return Err(response)
        }

        val ctaTt: CtaTt = try {
            response.body()
        } catch (e: JsonConvertException) {
            return Err(response)
        }

        return Ok(ctaTt.ctatt.eta.map(TtEta::toArrival))
    }
}

@Serializable
internal data class CtaTt(val ctatt: CtaEtas)

@Serializable
internal data class CtaEtas(
    val eta: List<TtEta>,
)

@Serializable
internal data class TtEta(
    val staNm: String,
    val stpDe: String,
    val rn: String,
    val rt: String,
    val destNm: String,
    val arrT: String,
    val isApp: String,
    val isDly: String,
) {
    fun toArrival() = Arrival(
        staNm,
        stpDe,
        rn,
        Line.apiLines[rt]!!,
        destNm,
        LocalDateTime.parse(arrT).format(formatter),
        isApp != "0",
        isDly != "0",
    )

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("h:mm a")
    }
}

@Serializable
data class Arrival(
    val stationName: String,
    val stopDescription: String,
    val runNumber: String,
    val line: Line,
    val destinationName: String,
    val arrivalTime: String,
    val isApproaching: Boolean,
    val isDelayed: Boolean,
)

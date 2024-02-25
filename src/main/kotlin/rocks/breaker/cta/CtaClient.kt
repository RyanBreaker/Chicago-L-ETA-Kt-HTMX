package rocks.breaker.cta

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.logging.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object CtaClient {
    private val LOGGER = KtorSimpleLogger("rocks.breaker.cta.CtaClient")

    private val key: String = System.getenv("CTA_APIKEY")

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            url {
                takeFrom("https://lapi.transitchicago.com/api/1.0/")
                parameters.append("outputType", "json")
                parameters.append("key", key)
            }
        }
    }

    suspend fun getEtas(stationId: String): Result<List<Eta>> {
        val ctaTt = client.get("ttarrivals.aspx") {
            parameter("mapid", stationId)
        }.body<TtArrivals>().ctaTt

        if (ctaTt.errorCode != "0" || ctaTt.errorMessage != null) {
            val error = """
                Problem getting Etas for ID: $stationId
                * Code: ${ctaTt.errorCode}
                * Message: ${ctaTt.errorMessage}""".trimIndent()
            LOGGER.warn(error)
            return Result.failure(Error(error))
        }

        return Result.success(ctaTt.etas)
    }

    @Serializable
    private data class TtArrivals(
        @SerialName("ctatt") val ctaTt: CtaTt,
    )

    @Serializable
    private data class CtaTt(
        @SerialName("errCd") val errorCode: String?,
        @SerialName("errNm") val errorMessage: String?,
        @SerialName("eta") val etas: List<Eta> = emptyList(),
    )
}

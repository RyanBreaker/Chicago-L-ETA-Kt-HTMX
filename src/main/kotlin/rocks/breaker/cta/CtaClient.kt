package rocks.breaker.cta

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class CtaClient {
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

    suspend fun getEtas(stationId: String): List<Eta> {
        val arrivals: TtArrivals = client.get("ttarrivals.aspx") {
            parameter("mapid", stationId)
        }.body()
        return arrivals.ctaTt.etas
    }

    @Serializable
    private data class CtaTt(@SerialName("eta") val etas: List<Eta>)

    @Serializable
    private data class TtArrivals(@SerialName("ctatt") val ctaTt: CtaTt)
}

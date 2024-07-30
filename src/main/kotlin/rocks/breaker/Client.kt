package rocks.breaker

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

object CtaClient {
    private val client = HttpClient(CIO) {
        install(HttpCache)
        install(ContentNegotiation) {
            json()
        }
    }
}

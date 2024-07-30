package rocks.breaker

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import rocks.breaker.plugins.configureHTTP
import rocks.breaker.plugins.configureMonitoring
import rocks.breaker.plugins.configureRouting
import rocks.breaker.plugins.configureSerialization

fun main() {
    if (System.getenv("KTOR_DEVELOPMENT").equals("true", ignoreCase = true))
        System.setProperty("io.ktor.development", "true")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureRouting()
    configureSerialization()
    configureMonitoring()
}

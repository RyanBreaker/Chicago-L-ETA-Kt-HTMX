package rocks.breaker.cta_tracker

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import rocks.breaker.cta_tracker.plugins.configureHTTP
import rocks.breaker.cta_tracker.plugins.configureMonitoring
import rocks.breaker.cta_tracker.plugins.configureRouting
import rocks.breaker.cta_tracker.plugins.configureSerialization

fun main() {
    if (System.getenv("KTOR_DEVELOPMENT").equals("true", ignoreCase = true))
        System.setProperty("io.ktor.development", "true")

    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureRouting()
    configureSerialization()
    configureMonitoring()
}

package rocks.breaker

import io.ktor.server.application.*
import rocks.breaker.plugins.configureHTTP
import rocks.breaker.plugins.configureMonitoring
import rocks.breaker.plugins.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureMonitoring()
    configureHTTP()
    configureRouting()
}

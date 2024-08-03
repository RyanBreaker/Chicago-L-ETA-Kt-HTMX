package rocks.breaker.cta_tracker.plugins

import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapError
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory
import rocks.breaker.cta_tracker.ArrivalsListTemplate
import rocks.breaker.cta_tracker.HtmxTemplate
import rocks.breaker.cta_tracker.MainPageTemplate
import rocks.breaker.cta_tracker.cta.Client
import rocks.breaker.cta_tracker.cta.allStations

fun Application.configureRouting() {
    val logger = LoggerFactory.getLogger("Routing")

    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(text = "404", status = status)
        }
        exception<Throwable> { call, cause ->
            logger.error("Unhandled error", cause)
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }

    routing {
        staticResources("/static", "static")

        get("/api/arrival/{mapId}") {
            val mapId = call.parameters["mapId"]
            if (mapId?.toIntOrNull() == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid Map ID")
                return@get
            }
            Client.getEtas(mapId).map {
                call.respond(it)
            }.mapError {
                call.respond(HttpStatusCode.InternalServerError, it.bodyAsText())
            }
        }

        get("/") {
            call.respondHtmlTemplate(MainPageTemplate()) {}
        }

        get("/stops") {
            call.respond(allStations)
        }

        get("/station/{mapId}") {
            val mapId = call.parameters["mapId"]!!
            call.respondHtmlTemplate(HtmxTemplate(ArrivalsListTemplate(mapId))) {}
        }
    }
}


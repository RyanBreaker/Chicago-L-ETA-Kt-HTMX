package rocks.breaker.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import rocks.breaker.cta.Station
import rocks.breaker.etasView
import rocks.breaker.htmx.hxGet

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondHtml {
                head {
                    title { +"CTA \"L\" Tracker" }
                    script { src = "https://unpkg.com/htmx.org@1.9.10" }
                    script { src = "https://cdn.tailwindcss.com" }
                }
                body {
                    h1 {
                        classes += ("text-xl")
                        +"CTA \"L\" Tracker"
                    }
                    ul {
                        Station.allStations.forEach {
                            li {
                                hxGet("/station/${it.id}")
                                +it.name
                            }
                        }
                    }
                }
            }
        }

        get("/station/{id}") {
            try {
                call.respondHtml {
                    body {
                        etasView(call.parameters["id"]!!)
                    }
                }
            } catch (e: Error) {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}

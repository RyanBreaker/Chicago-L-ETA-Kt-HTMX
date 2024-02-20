package rocks.breaker.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*
import rocks.breaker.cta.Station.Companion.readStations
import rocks.breaker.hxPost

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondHtml {
                head {
                    title { +"Bar" }
                    script { src = "https://unpkg.com/htmx.org@1.9.10" }
                    script { src = "https://cdn.tailwindcss.com" }
                }
                body {
                    h1 {
                        hxPost("/foo")
                        classes += ("text-xl")
                        +"CTA \"L\" Tracker"
                    }
                    ul {
                        readStations().forEach {
                            li { +"$it" }
                        }
                    }
                }
            }
        }
    }
}

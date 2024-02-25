package rocks.breaker.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*
import rocks.breaker.cta.CtaClient
import rocks.breaker.cta.Station
import rocks.breaker.htmx.hxGet
import java.time.Duration
import java.time.LocalDateTime

fun Application.configureRouting() {
    val client = CtaClient()
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
                        Station.readStations().forEach {
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
            val etas = client.getEtas(call.parameters["id"]!!)
            call.respondHtml {
                body {
                    ul {
                        etas.forEach {
                            li {
                                +"${it.description} ${parseTime(it.arrivalTime)}"
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun parseTime(dateTime: String): String {
    val time = LocalDateTime.parse(dateTime)
    val until = Duration.between(LocalDateTime.now(), time)
    return "${until.toMinutes()} minutes"
}

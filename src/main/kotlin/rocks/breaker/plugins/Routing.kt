package rocks.breaker.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import kotlinx.html.*
import rocks.breaker.cta.CtaClient

fun Application.configureRouting() {
    val client = CtaClient()
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
                        classes += ("text-xl")
                        +"CTA \"L\" Tracker"
                    }
//                    ul {
//                        readStations().forEach {
//                            li { +"$it" }
//                        }
//                    }
                    ul {
                        runBlocking {
                            client.getEtas("41320").forEach {
                                li {
                                    +"$it"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

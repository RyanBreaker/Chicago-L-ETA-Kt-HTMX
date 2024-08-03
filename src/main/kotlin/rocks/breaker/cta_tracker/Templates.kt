package rocks.breaker.cta_tracker

import io.ktor.server.html.*
import kotlinx.coroutines.runBlocking
import kotlinx.html.*
import org.intellij.lang.annotations.Language
import rocks.breaker.cta_tracker.cta.Client
import rocks.breaker.cta_tracker.cta.allStations

class HtmxTemplate<T : Template<FlowContent>>(private val template: T) : Template<HTML> {
    private val templateContent = TemplatePlaceholder<T>()

    override fun HTML.apply() {
        body { insert(template, templateContent) }
    }
}

class ArrivalsListTemplate(private val mapId: String) : Template<FlowContent> {
    private val arrivals = runBlocking { Client.getEtas(mapId) }.value

    override fun FlowContent.apply() {
        ul {
            for (arrival in arrivals) {
                li {
                    classes = setOf("line-${arrival.line.toString().lowercase()}")
                    unsafe {
                        +"${arrival.stationName} &mdash; ${arrival.arrivalTime}"
                    }
                }
            }
        }
    }
}

class MainPageTemplate : Template<HTML> {
    override fun HTML.apply() {
        lang = "en"
        attributes["data-theme"] = "light"

        head {
            title { +"""CTA "L" Tracker""" }
            meta { charset = "UTF-8" }
            meta {
                name = "viewport"
                content = "width=device-width, initial-scale=1"
            }
            link {
                rel = "icon"
                href = "/static/favicon.ico"
                type = "image/x-icon"
                sizes = "any"
            }

            // Daisy/Tailwind
            link {
                rel = "stylesheet"
                href = "https://cdn.jsdelivr.net/npm/daisyui@4.12.10/dist/full.min.css"
                type = "text/css"
            }
            script(src = "https://cdn.tailwindcss.com") { }

            // HTMX
            script(src = "https://unpkg.com/htmx.org@2.0.1") { }
            script(src = "https://unpkg.com/htmx.org/dist/ext/json-enc.js") { }
            script(src = "https://unpkg.com/htmx.org/dist/ext/preload.js") { }

            @Suppress("CssUnusedSymbol")
            @Language("CSS")
            val globalStyle = """
                .line-red {
                    background-color: #c60c30;
                }
                .line-blue {
                    background-color: #00a1de;
                }
                .line-brown {
                    background-color: #009b3a;
                }
                .line-green {
                    background-color: #009b3a;
                }
                .line-orange {
                    background-color: #f9461c;
                }
                .line-purple {
                    background-color: #522398;
                }
                .line-pink {
                    background-color: #e27ea6;
                }
                .line-yellow {
                    background-color: #f9e300;
                }
                .sign-grey {
                    background-color: #565a5c;
                }
            """.trimIndent()
            style {
                unsafe {
                    +globalStyle
                }
            }
        }

        body {
            attributes["hx-ext"] = "json-enc"

            header {
                +"""CTA "L" Tracker"""
            }

            main {
                ul {
                    for (station in allStations) {
                        li {
                            div {
                                attributes["hx-get"] = "/station/${station.mapId}"
                                +station.stopName
                            }
                        }
                    }
                }
            }

            footer {
                +"Baz"
            }
        }
    }
}

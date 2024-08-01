package rocks.breaker.plugins

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
import io.ktor.util.pipeline.*
import kotlinx.html.*
import org.intellij.lang.annotations.Language
import org.slf4j.LoggerFactory
import rocks.breaker.cta.Client
import rocks.breaker.cta.allStops

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
            Client.getEtas(mapId)
                .map {
                    call.respond(it)
                }
                .mapError {
                    call.respond(HttpStatusCode.InternalServerError, it.bodyAsText())
                }
        }

        get("/") {
            renderMainPage(this)
        }

        get("/stops") {
            call.respond(allStops)
        }
    }
}

suspend fun renderMainPage(context: PipelineContext<Unit, ApplicationCall>) {
    with(context) {
        call.respondHtmlTemplate(MainTemplate(template = SelectionTemplate())) {
            templateContent {
                selectionTemplate {
                    section {
                        div {
                            +"Foobar lol"
                        }
                    }
                }
            }
        }
    }
}

class SelectionTemplate : Template<FlowContent> {
    val selectionTemplate = Placeholder<FlowContent>()
    override fun FlowContent.apply() {
        insert(selectionTemplate)
    }

}

class MainTemplate<T : Template<FlowContent>>(private val template: T) : Template<HTML> {
    val templateContent = TemplatePlaceholder<T>()
    val headerContent = Placeholder<FlowContent>()

    override fun HTML.apply() {
        lang = "en"
        attributes["data-theme"] = "light"

        head {
            title { +"HTMX and KTor <3" }
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
            link {
                rel = "stylesheet"
                href = "https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css"
            }
            script(src = "https://unpkg.com/htmx.org@2.0.1") { }
            script(src = "https://unpkg.com/htmx.org/dist/ext/json-enc.js") { }
            script(src = "https://unpkg.com/htmx.org/dist/ext/preload.js") { }

            @Language("CSS") val globalStyle = """
                    #choices {
                        display: grid; /* Enables grid layout */
                        grid-template-columns: repeat(auto-fit, minmax(15em, 1fr)); /* Adjust the number of columns based on the width of the container */
                        /* Key line for responsiveness: */
                        gap: 20px; /* Adjust the spacing between items */
            
                        a {
                            display: block;
                        }
                    }
                    
                    .htmx-indicator {
                        visibility: hidden;
                    }
                                        
                    .htmx-request .htmx-indicator {
                        visibility: visible;
                    }
                                        
                    .box {
                        border: 1px solid red;
                        border-radius: 0.5em;
                        text-align: center;
                        padding: 1em;                    
                    }
                                        
                    section {
                        margin-bottom: 2em;
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

            div {
                style = "max-width: 90vw; margin: auto;"

                header {
                    +"Foo"
                }

                main {
                    +"Bar"
                }

                footer {
                    +"Baz"
                }
            }
        }
    }
}

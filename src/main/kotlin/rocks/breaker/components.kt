package rocks.breaker

import kotlinx.coroutines.runBlocking
import kotlinx.html.FlowContent
import kotlinx.html.li
import kotlinx.html.ul
import rocks.breaker.cta.CtaClient
import java.time.Duration
import java.time.LocalDateTime

fun FlowContent.etasView(id: String) {
    val etas = runBlocking {
        CtaClient.getEtas(id)
    }.getOrThrow()

    ul {
        // TODO: Due if 0 minutes?
        etas.forEach {
            val arrival = if (it.isApproaching) {
                "Due"
            } else {
                val time = LocalDateTime.parse(it.arrivalTime)
                val minutes = Duration.between(LocalDateTime.now(), time)!!.toMinutes()
                "$minutes Minute${if (minutes == 1L) "" else "s"}"
            }

            li {
                +"${it.description} $arrival"
            }
        }
    }
}

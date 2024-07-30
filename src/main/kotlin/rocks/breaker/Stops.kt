package rocks.breaker

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException

private fun getFile(): File {
    val resource = Thread.currentThread().contextClassLoader.getResource("stops.csv")
        ?: throw FileNotFoundException("Cannot find stops.csv")
    return File(resource.toURI())
}

enum class Line {
    Red, Blue, Green, Brown, Purple, Yellow, Pink, Orange;

    companion object {
        val csvLines = arrayOf("RED", "BLUE", "G", "BRN", "P", "Y", "Pnk", "O")

        fun fromString(s: String?): Line? {
            return when (s) {
                "RED" -> Red
                "BLUE" -> Blue
                "G" -> Green
                "BRN" -> Brown
                "P" -> Purple
                "Y" -> Yellow
                "Pnk" -> Pink
                "O" -> Orange
                else -> null
            }
        }
    }
}

@Serializable
data class Stop(val stopId: String, val stopName: String, val lines: List<Line>)

val allStops by lazy {
    val logger = LoggerFactory.getLogger("allStops")
    logger.info(System.getProperty("user.dir"))
    csvReader().open(getFile()) {
        readAllWithHeaderAsSequence().map {
            Stop(
                it["STOP_ID"]!!,
                it["STOP_NAME"]!!,
                getLinesFromRow(it)
            )
        }.toList()
    }
}

fun getLinesFromRow(row: Map<String, String>): List<Line> = Line.csvLines
    .filter { row[it] == "true" }
    .mapNotNull { Line.fromString(it) }

package rocks.breaker.cta_tracker.cta

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.serialization.Serializable
import java.io.File
import java.io.FileNotFoundException

private fun getFile(): File {
    val resource = Thread.currentThread().contextClassLoader.getResource("stops.csv")
        ?: throw FileNotFoundException("Cannot find stops.csv")
    return File(resource.toURI())
}

@Serializable
enum class Line {
    Red, Blue, Green, Brown, Purple, Yellow, Pink, Orange;

    companion object {
        fun fromString(str: String): Line? =
            when (str.uppercase()) {
                "RED" -> Red
                "BLUE" -> Blue
                "G" -> Green
                "BRN" -> Brown
                "P" -> Purple
                "Y" -> Yellow
                "PNK", "PINK" -> Pink
                "O", "ORG" -> Orange
                else -> null
            }

        // Conversion map from CSV columns
        val csvLines = mapOf(
            "RED" to Red,
            "BLUE" to Blue,
            "G" to Green,
            "BRN" to Brown,
            "P" to Purple,
            "Y" to Yellow,
            "Pnk" to Pink,
            "O" to Orange
        )

        // Conversion map from API route field input
        val apiLines = mapOf(
            "Red" to Red,
            "Blue" to Blue,
            "G" to Green,
            "Brn" to Brown,
            "P" to Purple,
            "Y" to Yellow,
            "Pink" to Pink,
            "Org" to Orange,
        )
    }
}

@Serializable
data class Station(val mapId: String, val stopName: String, val isAda: Boolean, val lines: List<Line>)

val allStations by lazy {
    csvReader().open(getFile()) {
        readAllWithHeaderAsSequence().distinctBy { it["MAP_ID"] }.map {
            Station(
                it["MAP_ID"]!!, it["STATION_NAME"]!!, it["ADA"]!! == "true", getLinesFromRow(it)
            )
        }.toList()
    }
}

internal fun getLinesFromRow(row: Map<String, String>): List<Line> =
    Line.csvLines.filter { row[it.key] == "true" }.map { it.value }

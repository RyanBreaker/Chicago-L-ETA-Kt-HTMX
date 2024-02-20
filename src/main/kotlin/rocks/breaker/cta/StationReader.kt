package rocks.breaker.cta

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

data class Station(val id: Int, val stopName: String, val handicapAccessible: Boolean) {
    companion object {
        fun readStations(): List<Station> {
            val stops = File("cta/stops.txt")
            val rows = csvReader().readAllWithHeader(stops)
                .filter { it[STOP_ID]?.toInt() in 40000..49999 }
                .map { Station(it[STOP_ID]!!.toInt(), it[STOP_NAME]!!, it[HANDICAP]!! == "1") }
            return rows
        }

        private const val STOP_ID = "stop_id"
        private const val STOP_NAME = "stop_name"
        private const val HANDICAP = "wheelchair_boarding"
    }
}

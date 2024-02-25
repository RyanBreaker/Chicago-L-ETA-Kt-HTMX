package rocks.breaker.cta

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

data class Station(val id: Int, val name: String, val isHandicapAccessible: Boolean) {
    companion object {
        val allStations: List<Station> by lazy {
            csvReader().readAllWithHeader(File("cta/stops.txt"))
                .filter { it[STOP_ID]?.toInt() in 40000..49999 }
                .map { Station(it[STOP_ID]!!.toInt(), it[STOP_NAME]!!, it[HANDICAP]!! == "1") }
        }

        private const val STOP_ID = "stop_id"
        private const val STOP_NAME = "stop_name"
        private const val HANDICAP = "wheelchair_boarding"
    }
}

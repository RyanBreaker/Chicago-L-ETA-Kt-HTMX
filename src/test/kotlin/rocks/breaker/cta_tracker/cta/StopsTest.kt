package rocks.breaker.cta_tracker.cta

import org.junit.Test

internal class StopsTest {
    @Test
    fun `test getLinesFromRow`() {
        val row = mapOf("RED" to "false", "BLUE" to "false", "Y" to "true")
        val lines = getLinesFromRow(row)
        assert(lines.size == 1)
        assert(lines[0] == Line.Yellow)
    }
}
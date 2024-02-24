package rocks.breaker.cta

enum class Route(val id: String, val url: String, val color: String, val textColor: String) {
    Red("Red", "https://www.transitchicago.com/redline/", "#C60C30", "#FFFFFF"),
    Purple("P", "https://www.transitchicago.com/purpleline/", "#522398", "#FFFFFF"),
    Yellow("Y", "https://www.transitchicago.com/yellowline/", "#F9E300", "#000000"),
    Blue("Blue", "https://www.transitchicago.com/blueline/", "#00A1DE", "#FFFFFF"),
    Pink("Pink", "https://www.transitchicago.com/pinkline/", "#E27EA6", "#FFFFFF"),
    Green("G", "https://www.transitchicago.com/greenline/", "#009B3A", "#FFFFFF"),
    Orange("Org", "https://www.transitchicago.com/orangeline/", "#F9461C", "#FFFFFF"),
    Brown("Brn", "https://www.transitchicago.com/brownline/", "#62361B", "#FFFFFF"),
    ;

    override fun toString(): String = "${this.name} Line"

    companion object {
        fun getById(id: String): Route? = entries.firstOrNull { it.id == id }
    }
}

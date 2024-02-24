package rocks.breaker.cta

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Eta(
    @SerialName("staId")
    val stationId: String,
    @SerialName("staNm")
    val stationName: String,
    @SerialName("stpDe")
    val description: String,
    @SerialName("rn")
    val runNumber: String,
    @SerialName("rt")
    val routeId: String,
    @SerialName("arrT")
    val arrivalTime: String,
    @SerialName("isApp")
    private val _isApproaching: String,
    @SerialName("isSch")
    private val _isScheduled: String,
    @SerialName("isDly")
    private val _isDelayed: String,
    @SerialName("isFlt")
    private val _isFault: String,
) {
    val isApproaching get() = _isApproaching == "1"
    val isScheduled get() = _isScheduled == "1"
    val isDelayed get() = _isDelayed == "1"
    val isFault get() = _isFault == "1"
}

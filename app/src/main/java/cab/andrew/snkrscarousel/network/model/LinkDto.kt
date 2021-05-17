package cab.andrew.snkrscarousel.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LinkDto (
    @Json(name = "stockx")
    var stockx: String?,
    @Json(name = "goat")
    var goat: String?,
    @Json(name = "flightClub")
    var flightClub: String?
)
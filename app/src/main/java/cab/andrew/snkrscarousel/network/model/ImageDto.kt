package cab.andrew.snkrscarousel.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDto (
    @Json(name = "original")
    var original: String?,
    @Json(name = "small")
    var small: String?,
    @Json(name = "thumbnail")
    var thumbnail: String?
)
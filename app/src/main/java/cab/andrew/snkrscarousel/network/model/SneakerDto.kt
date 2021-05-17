package cab.andrew.snkrscarousel.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SneakerDto (
        var id: String,
        @Json(name = "name")
        var name: String,
        @Json(name = "sku")
        var sku: String,
        @Json(name = "brand")
        var brand: String,
        @Json(name = "gender")
        var gender: String,
        @Json(name = "colorway")
        var colorway: String,
        @Json(name = "story")
        var story: String,
        @Json(name = "retailPrice")
        var retailPrice: Int,
        @Json(name = "estimatedMarketValue")
        var estimatedMarketValue: Int,
        @Json(name = "releaseDate")
        var releaseDate: String,
        @Json(name = "releaseYear")
        var releaseYear: String,
        @Json(name = "silhouette")
        var silhouette: String,
        @Json(name = "image")
        var image: ImageDto,
        @Json(name = "links")
        var links: LinkDto
) {
        @JsonClass(generateAdapter = true)
        data class ImageDto (
                @Json(name = "original")
                var original: String?,
                @Json(name = "small")
                var small: String?,
                @Json(name = "thumbnail")
                var thumbnail: String?
        )


        @JsonClass(generateAdapter = true)
        data class LinkDto (
                @Json(name = "stockx")
                var stockx: String?,
                @Json(name = "goat")
                var goat: String?,
                @Json(name = "flightClub")
                var flightClub: String?
        )
}
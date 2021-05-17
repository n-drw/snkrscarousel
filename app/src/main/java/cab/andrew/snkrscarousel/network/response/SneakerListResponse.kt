package cab.andrew.snkrscarousel.network.response

import cab.andrew.snkrscarousel.network.model.SneakerDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SneakerListResponse(
    @Json(name = "results")
    val sneakers: List<SneakerDto> = listOf()
)
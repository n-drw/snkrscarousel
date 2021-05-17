package cab.andrew.snkrscarousel.domain.models

import cab.andrew.snkrscarousel.network.model.ImageDto
import cab.andrew.snkrscarousel.network.model.LinkDto
import cab.andrew.snkrscarousel.network.model.SneakerDto

data class Sneaker(
    val id: String,
    val name: String,
    val sku: String,
    val brand: String,
    val gender: String,
    val colorway: String,
    val story: String,
    val retailPrice: Int,
    val estimatedMarketValue: Int,
    val releaseDate: String,
    val releaseYear: String,
    val silhouette: String,
    val image: SneakerDto.ImageDto,
    val links: SneakerDto.LinkDto
)

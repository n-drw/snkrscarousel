package cab.andrew.snkrscarousel.cache.model

import android.media.Image
import androidx.room.TypeConverter
import cab.andrew.snkrscarousel.domain.DomainMapper
import cab.andrew.snkrscarousel.domain.models.Sneaker
import cab.andrew.snkrscarousel.domain.models.SneakerEntity
import cab.andrew.snkrscarousel.network.model.ImageDto
import java.util.ArrayList

class SneakerEntityMapper : DomainMapper<SneakerEntity, Sneaker> {
    override fun mapToDomainModel(model: SneakerEntity): Sneaker {
        return Sneaker(
            id = model.id,
            name = model.name,
            sku = model.sku,
            brand = model.brand,
            gender = model.gender,
            colorway = model.colorway,
            story = model.story,
            retailPrice = model.retailPrice,
            estimatedMarketValue = model.estimatedMarketValue,
            releaseDate = model.releaseDate,
            releaseYear = model.releaseYear,
            silhouette = model.silhouette,
            image = model.image,
            links = model.links
        )
    }

    override fun mapFromDomainModel(domainModel: Sneaker): SneakerEntity {
        return SneakerEntity(
            id = domainModel.id,
            name = domainModel.name,
            sku = domainModel.sku,
            brand = domainModel.brand,
            gender = domainModel.gender,
            colorway = domainModel.colorway,
            story = domainModel.story,
            retailPrice = domainModel.retailPrice,
            estimatedMarketValue = domainModel.estimatedMarketValue,
            releaseDate = domainModel.releaseDate,
            releaseYear = domainModel.releaseYear,
            silhouette = domainModel.silhouette,
            image = domainModel.image,
            links = domainModel.links
        )
    }

    fun fromEntityList(initial: List<SneakerEntity>): List<Sneaker> {
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Sneaker>): List<SneakerEntity> {
        return initial.map {mapFromDomainModel(it)}
    }
}
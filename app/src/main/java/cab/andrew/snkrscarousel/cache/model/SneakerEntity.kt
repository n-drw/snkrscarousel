package cab.andrew.snkrscarousel.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import cab.andrew.snkrscarousel.network.model.ImageDto
import cab.andrew.snkrscarousel.network.model.LinkDto
import cab.andrew.snkrscarousel.network.model.SneakerDto

@Entity(tableName = "sneakers")
data class SneakerEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "sku")
    var sku: String,
    @ColumnInfo(name = "brand")
    var brand: String,
    @ColumnInfo(name = "gender")
    var gender: String,
    @ColumnInfo(name = "colorway")
    var colorway: String,
    @ColumnInfo(name = "story")
    var story: String,
    @ColumnInfo(name = "retail_price")
    var retailPrice: Int,
    @ColumnInfo(name = "estimated_market_value")
    var estimatedMarketValue: Int,
    @ColumnInfo(name = "release_date")
    var releaseDate: String,
    @ColumnInfo(name = "release_year")
    var releaseYear: String,
    @ColumnInfo(name = "silhouette")
    var silhouette: String,
    @ColumnInfo(name = "image")
    var image: SneakerDto.ImageDto,
    @ColumnInfo(name = "links")
    var links: SneakerDto.LinkDto
)

package cab.andrew.snkrscarousel.network.model.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import cab.andrew.snkrscarousel.network.model.ImageDto
import cab.andrew.snkrscarousel.network.model.SneakerDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class ImageTypeConverter @Inject constructor(
    private val moshi: Moshi
) {

    @TypeConverter
    fun fromString(value: String): SneakerDto.ImageDto? {
        val adapter: JsonAdapter<SneakerDto.ImageDto> = moshi.adapter(SneakerDto.ImageDto::class.java)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromImageType(type: SneakerDto.ImageDto?): String? {
        val adapter: JsonAdapter<SneakerDto.ImageDto> = moshi.adapter(SneakerDto.ImageDto::class.java)
        return adapter.toJson(type)
    }
}
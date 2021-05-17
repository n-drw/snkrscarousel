package cab.andrew.snkrscarousel.network.model.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import cab.andrew.snkrscarousel.network.model.LinkDto
import cab.andrew.snkrscarousel.network.model.SneakerDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class LinksTypeConverter @Inject constructor(
    private val moshi: Moshi
) {

    @TypeConverter
    fun fromString(value: String): SneakerDto.LinkDto? {
        val adapter: JsonAdapter<SneakerDto.LinkDto> = moshi.adapter(SneakerDto.LinkDto::class.java)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromLinkType(type: SneakerDto.LinkDto?): String {
        val adapter: JsonAdapter<SneakerDto.LinkDto> = moshi.adapter(SneakerDto.LinkDto::class.java)
        return adapter.toJson(type)
    }
}
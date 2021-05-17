package cab.andrew.snkrscarousel.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import cab.andrew.snkrscarousel.cache.SneakerDao
import cab.andrew.snkrscarousel.domain.models.SneakerEntity
import cab.andrew.snkrscarousel.network.model.converter.ImageTypeConverter
import cab.andrew.snkrscarousel.network.model.converter.LinksTypeConverter

@Database(entities = [SneakerEntity::class], version = 1, exportSchema = false)
@TypeConverters(value = [ImageTypeConverter::class, LinksTypeConverter::class])
abstract class SneakerDatabase : RoomDatabase() {
    abstract fun sneakerDao(): SneakerDao

    companion object{
        const val SNEAKERS_DATABASE = "sneakers_db"
    }

    @Volatile
    private var INSTANCE: SneakerDatabase? = null
}
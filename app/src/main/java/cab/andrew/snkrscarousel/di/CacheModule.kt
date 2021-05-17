package cab.andrew.snkrscarousel.di

import androidx.room.Room
import cab.andrew.snkrscarousel.cache.SneakerDao
import cab.andrew.snkrscarousel.cache.database.SneakerDatabase
import cab.andrew.snkrscarousel.cache.model.SneakerEntityMapper
import cab.andrew.snkrscarousel.network.model.converter.ImageTypeConverter
import cab.andrew.snkrscarousel.network.model.converter.LinksTypeConverter
import cab.andrew.snkrscarousel.presentation.BaseApplication
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication,
                  imageTypeConverter: ImageTypeConverter,
                  linksTypeConverter: LinksTypeConverter
    ) : SneakerDatabase {
       return Room
           .databaseBuilder(app, SneakerDatabase::class.java, SneakerDatabase.SNEAKERS_DATABASE)
           .fallbackToDestructiveMigration()
           .addTypeConverter(imageTypeConverter)
           .addTypeConverter(linksTypeConverter)
           .build()
    }

    @Singleton
    @Provides
    fun provideSneakerDao(app: SneakerDatabase) : SneakerDao {
        return app.sneakerDao()
    }

    @Singleton
    @Provides
    fun provideCacheSneakerMapper(): SneakerEntityMapper {
        return SneakerEntityMapper()
    }

    @Singleton
    @Provides
    fun provideImageTypeResponseConverter(moshi: Moshi): ImageTypeConverter {
        return ImageTypeConverter(moshi)
    }

    @Singleton
    @Provides
    fun provideLinksTypeResponseConverter(moshi: Moshi): LinksTypeConverter {
        return LinksTypeConverter(moshi)
    }
}
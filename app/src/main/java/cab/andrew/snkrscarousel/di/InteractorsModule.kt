package cab.andrew.snkrscarousel.di

import androidx.room.Room
import cab.andrew.snkrscarousel.cache.SneakerDao
import cab.andrew.snkrscarousel.cache.database.SneakerDatabase
import cab.andrew.snkrscarousel.cache.model.SneakerEntityMapper
import cab.andrew.snkrscarousel.domain.interactors.GetSneaker
import cab.andrew.snkrscarousel.network.RetrofitService
import cab.andrew.snkrscarousel.network.model.SneakerDtoMapper
import cab.andrew.snkrscarousel.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetSneaker(
        sneakerService: RetrofitService,
        sneakerDao: SneakerDao,
        entityMapper: SneakerEntityMapper,
        dtoMapper: SneakerDtoMapper
    ): GetSneaker {
        return GetSneaker(
            sneakerService = sneakerService,
            sneakerDao = sneakerDao,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper
        )
    }


}
package cab.andrew.snkrscarousel.domain.interactors

import android.util.Log
import cab.andrew.snkrscarousel.cache.SneakerDao
import cab.andrew.snkrscarousel.cache.model.SneakerEntityMapper
import cab.andrew.snkrscarousel.domain.data.DataState
import cab.andrew.snkrscarousel.domain.models.Sneaker
import cab.andrew.snkrscarousel.network.RetrofitService
import cab.andrew.snkrscarousel.network.model.SneakerDtoMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetSneaker(
    private val sneakerDao: SneakerDao,
    private val sneakerService: RetrofitService,
    private val entityMapper: SneakerEntityMapper,
    private val dtoMapper: SneakerDtoMapper
) {
    fun execute(): Flow<DataState<List<Sneaker>>> = flow {
        try {
            emit(DataState.loading<List<Sneaker>>())
            val sneakers = getSneakersFromNetwork()

            sneakerDao.insertAllSneakers(entityMapper.toEntityList(sneakers))

            val cacheResult = sneakerDao.getAllSneakers()
            val list = entityMapper.fromEntityList(cacheResult)
            emit(DataState.success(list))

        } catch (e: Exception) {
            emit(DataState.error<List<Sneaker>>(e.message ?: "Error"))
        }
    }

    private suspend fun getSneakersFromNetwork(): List<Sneaker> {
        return dtoMapper.toDomainList(
            sneakerService.get().sneakers
        )
    }
}
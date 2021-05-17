package cab.andrew.snkrscarousel.cache

import androidx.room.*
import cab.andrew.snkrscarousel.domain.models.SneakerEntity

@Dao
interface SneakerDao {
    @Transaction
    @Query("select * from sneakers")
    suspend fun getAllSneakers(): List<SneakerEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllSneakers(sneakers: List<SneakerEntity>): LongArray

    @Insert
    suspend fun insertSneaker(sneaker: SneakerEntity): Long
}
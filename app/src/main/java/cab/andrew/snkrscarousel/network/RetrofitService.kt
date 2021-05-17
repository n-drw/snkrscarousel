package cab.andrew.snkrscarousel.network

import cab.andrew.snkrscarousel.network.response.SneakerListResponse
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {
    @GET("/sneakers")
    suspend fun get(): SneakerListResponse
}
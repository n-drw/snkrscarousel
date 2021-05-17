package cab.andrew.snkrscarousel.di

import android.content.Context
import cab.andrew.snkrscarousel.BuildConfig
import cab.andrew.snkrscarousel.network.RetrofitService
import cab.andrew.snkrscarousel.network.model.SneakerDtoMapper
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val SNEAKERS_API_URL = "https://the-sneaker-database.p.rapidapi.com/"

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val original = it.request()
            val request = original.newBuilder()
                .get()
                .addHeader("x-rapidapi-key", BuildConfig.RAPID_API_KEY)
                .addHeader("x-rapidapi-host", BuildConfig.RAPID_API_HOST)
                .build()
            it.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(provideHttpLoggingInterceptor())
            .addInterceptor(provideHeaderInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshiConverterFactory: MoshiConverterFactory): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(SNEAKERS_API_URL)
            .addConverterFactory(moshiConverterFactory)
            .client(
                provideOkHttpClient().newBuilder().addInterceptor { chain ->
                    val req = chain.request()
                    val originalUrl = req.url
                    val newUrl = originalUrl.newBuilder()
                        .addQueryParameter("limit", "100")
                        .build()

                    chain.proceed(
                        req.newBuilder()
                            .url(newUrl)
                            .build()
                    )
                }.build()
            )
            .build()
            .create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideSneakerMapper(): SneakerDtoMapper {
        return SneakerDtoMapper()
    }

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create()

    @Provides
    fun provideSneakerApiUrl(): String = SNEAKERS_API_URL
}
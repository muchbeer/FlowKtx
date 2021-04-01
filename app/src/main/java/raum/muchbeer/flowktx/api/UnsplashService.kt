package raum.muchbeer.flowktx.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import raum.muchbeer.flowktx.BuildConfig

import raum.muchbeer.flowktx.model.UnsplashSearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface UnsplashService {

    @GET("search/photos")
    suspend fun searchPhoto(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String = BuildConfig.UNSPLASH_ACCESS_KEY
    ) : UnsplashSearchResponse

    companion object {


        fun create(): UnsplashService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UnsplashService::class.java)
        }
    }
}
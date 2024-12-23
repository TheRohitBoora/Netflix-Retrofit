package com.trb.netflix

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitHelper {
    @GET(".")
    suspend fun movieList(): List<Movies>

    companion object {
        private const val BASE_URL = "https://imdb-top-100-movies.p.rapidapi.com/"

        private val retrofitClient = OkHttpClient.Builder().apply {
            addInterceptor(

                Interceptor { chin ->
                    val build = chin.request().newBuilder()
                    build.header(
                        "x-rapidapi-key",
                        "478775d941msh09f4368a176dd6dp1e528ajsn07ec8c6f9aed"
                    )
                    return@Interceptor chin.proceed(build.build())
                }
            )
        }.build()
        fun getInstance(): RetrofitHelper {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(retrofitClient)
                .build()
            return retrofit.create(RetrofitHelper::class.java)
        }
    }

}
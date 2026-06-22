package com.example.wellness.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class HealthArticle(val id: Int, val title: String, val body: String)

interface WellnessApi {
    @GET("af2f945442c1fd01b942")
    suspend fun getHealthArticles(): List<HealthArticle>
}

object RetrofitClient {
    private const val BASE_URL = "https://api.npoint.io/"

    val api: WellnessApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WellnessApi::class.java)
    }
}

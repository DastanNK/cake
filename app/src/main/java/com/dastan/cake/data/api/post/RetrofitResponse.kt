package com.dastan.cake.data.api.post

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitResponse {
    private const val BASE_URL = "https://raw.githubusercontent.com/DastanNK/cakeApi/refs/heads/main/"

    val instance: ApiPost by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiPost::class.java)
    }
}
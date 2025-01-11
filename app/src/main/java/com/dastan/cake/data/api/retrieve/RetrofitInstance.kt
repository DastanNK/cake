package com.dastan.cake.data.api.retrieve

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File



object RetrofitInstance {
    private const val BASE_URL = "https://raw.githubusercontent.com/"
    private val cacheSize = 10 * 1024 * 1024
    private val cacheDirectory = File("cache_directory").apply {
        if (!exists()) {
            mkdirs()
        }
    }

    private val cache = Cache(cacheDirectory, cacheSize.toLong())

    private val okHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}
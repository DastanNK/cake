package com.dastan.cake.data

import retrofit2.http.GET

interface Api {

    @GET("DastanNK/cakeApi/refs/heads/main/api.json")
    suspend fun retrieveData(): CakeResponse

}
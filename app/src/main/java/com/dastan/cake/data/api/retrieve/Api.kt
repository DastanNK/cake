package com.dastan.cake.data.api.retrieve

import com.dastan.cake.data.model.CakeResponse
import retrofit2.http.GET

interface Api {

    @GET("DastanNK/cakeApi/refs/heads/main/api.json")
    suspend fun retrieveData(): CakeResponse

}
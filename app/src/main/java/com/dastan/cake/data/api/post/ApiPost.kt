package com.dastan.cake.data.api.post

import com.dastan.cake.data.model.CakeOrderSubmission
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiPost {
    @POST("apiPost2")
    suspend fun createPost(@Body cakeOrderSubmission: CakeOrderSubmission): Response<CakeOrderSubmission>
}
package com.example.dishdash.ML.retrofit

import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface MLService {
    @POST("user_id")
    suspend fun postId(
        @Field("id") id : String
    )
}
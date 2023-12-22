package com.example.dishdash.retrofit

import com.example.dishdash.data.response.DataUser
import com.example.dishdash.data.response.FoodResponse
import com.example.dishdash.data.response.FoodResponseItem
import com.example.dishdash.data.response.LoginResponse
import com.example.dishdash.data.response.PhotoResponse
import com.example.dishdash.data.response.RegisterResponse
import com.example.dishdash.data.response.RestoResponseItem
import com.example.dishdash.database.FavoriteRestoDao
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService : FavoriteRestoDao{

    @FormUrlEncoded
    @POST("users/register")
    suspend fun register(
        @Field("username") username : String,
        @Field("email") email: String,
        @Field("password") password : String,
    ) : RegisterResponse


    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password")password: String
    ) : LoginResponse

    @GET("users/:id")
    fun getUserById() : Call<DataUser>

    @GET("food")
    fun getFoods() : Call<List<FoodResponseItem>>

    @GET("restaurant")
    fun getResto() : Call<List<RestoResponseItem>>

    @GET("listResto")
    fun getDetailResto() : Call<List<RestoResponseItem>>

    @GET("listResto")
    fun getFoodResto() : Call<List<RestoResponseItem>>

}
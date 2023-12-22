package com.example.dishdash.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.dishdash.data.Result
import com.example.dishdash.data.UserModel
import com.example.dishdash.data.pref.UserPreference
import com.example.dishdash.data.response.FoodResponseItem
import com.example.dishdash.data.response.LoginResponse
import com.example.dishdash.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.lang.Exception

class MyRepostiory private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
){
    fun register( username : String, email : String, password : String) = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.register(username, email, password)
            emit(Result.Success(response))
        } catch (e : Exception){
            emit(Result.Error(e.message.toString()))
        }
    }
    fun login(email: String,password: String) = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            val data = response.message
            emit(Result.Success(data))
        }catch (e : HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(Result.Error(errorResponse.message!!))
        }
    }

    fun getSession() : Flow<UserModel> {
        return userPreference.getSession()
    }
//    fun getFoods() : LiveData<PagingData<FoodResponseItem>>{
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            pagingSourceFactory = {
//                FoodPagingSource(apiService)
//            }
//        ).liveData
//    }

    suspend fun saveSession(user : UserModel){
        userPreference.saveSession(user)
    }
    suspend fun logout(){
        userPreference.logout()
    }

    companion object {
        private const val TAG = "MyRepository"
        @Volatile
        private var instance: MyRepostiory?=null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ) : MyRepostiory = instance ?: synchronized(this){
            instance ?: MyRepostiory(apiService,userPreference)
        }.also {
            instance = it
        }
    }
}
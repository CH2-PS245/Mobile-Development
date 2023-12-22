package com.example.dishdash.injection

import android.content.Context
import com.example.dishdash.data.pref.UserPreference
import com.example.dishdash.data.pref.datastore
import com.example.dishdash.repository.MyRepostiory
import com.example.dishdash.retrofit.ApiConfig
import com.example.dishdash.retrofit.ApiService

object Injection {
    fun provideRepository(context: Context):MyRepostiory{
        val apiService = ApiConfig.getApiService()
        val userPreference = UserPreference.getInstance(context.datastore)
        return MyRepostiory.getInstance(apiService,userPreference)
    }
}
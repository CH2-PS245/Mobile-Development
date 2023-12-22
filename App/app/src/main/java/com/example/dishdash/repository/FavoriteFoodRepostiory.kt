package com.example.dishdash.repository

import androidx.lifecycle.LiveData
import com.example.dishdash.database.FavoriteRestoDao
import com.example.dishdash.database.RestoEntity
import com.example.dishdash.retrofit.ApiService
import com.example.dishdash.utils.AppExecutors
import java.util.concurrent.Executors

class FavoriteFoodRepostiory private constructor(
    private val apiService: ApiService,
    private val mFavoriteRestoDao: FavoriteRestoDao,
    private val appExecutors: AppExecutors
){
    fun isFavoriteResto(name : String) : LiveData<RestoEntity>{
        return mFavoriteRestoDao.isFavoriteFood(name)
    }

    fun getFavoriteUsers(): LiveData<List<RestoEntity>>{
        return mFavoriteRestoDao.getFavoriteResto()
    }

    fun insert(restoEntity: RestoEntity){
        appExecutors.diskIO.execute{
            mFavoriteRestoDao.addToFavorite(restoEntity)
        }
    }

    fun delete(restoEntity: RestoEntity){
        appExecutors.diskIO.execute {
            mFavoriteRestoDao.delete(restoEntity)
        }
    }

    companion object {
        @Volatile
        private var instance : FavoriteFoodRepostiory?=null
        fun getInstance(
            apiService: ApiService,
            favoriteRestoDao: FavoriteRestoDao,
            appExecutors: AppExecutors
        ) : FavoriteFoodRepostiory = instance ?: synchronized(this){
            instance ?: FavoriteFoodRepostiory(apiService,favoriteRestoDao,appExecutors)
        } .also { instance = it }
    }
}
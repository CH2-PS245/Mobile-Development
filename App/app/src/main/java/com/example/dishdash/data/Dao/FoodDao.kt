package com.example.dishdash.data.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dishdash.data.response.FoodResponse

//@Dao
//interface FoodDao {
//    @Query("SELECT * FROM table_food")
//    fun getDataFood() : LiveData<List<FoodResponse>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(food: FoodResponse)
//}
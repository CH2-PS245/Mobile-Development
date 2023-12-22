package com.example.dishdash.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteRestoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(favoriteResto: RestoEntity)

    @Query("SELECT * FROM favorite_resto WHERE name = :name")
    fun isFavoriteFood(name : String) : LiveData<RestoEntity>

    @Delete
    fun delete(favoriteResto: RestoEntity)

    @Query("SELECT * FROM favorite_resto")
    fun getFavoriteResto() : LiveData<List<RestoEntity>>
}
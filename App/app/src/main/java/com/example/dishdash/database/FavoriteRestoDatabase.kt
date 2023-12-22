package com.example.dishdash.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [RestoEntity::class], version = 2)
abstract class FavoriteRestoDatabase : RoomDatabase() {
    abstract fun favoriteRestoDao(): FavoriteRestoDao

    companion object {
        private var INSTANCE : FavoriteRestoDatabase?=null
        fun getDatabase(context: Context):FavoriteRestoDatabase{
            if (INSTANCE == null){
                synchronized(FavoriteRestoDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,FavoriteRestoDatabase::class.java,"favorite_resto")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavoriteRestoDatabase
        }
    }
}
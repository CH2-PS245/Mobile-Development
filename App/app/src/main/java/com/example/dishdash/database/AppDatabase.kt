package com.example.dishdash.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dishdash.data.Dao.FeedbackDao

@Database(entities = [FeedbackEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedbackDao(): FeedbackDao
}

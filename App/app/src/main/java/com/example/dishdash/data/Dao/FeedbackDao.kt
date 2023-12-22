package com.example.dishdash.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dishdash.database.FeedbackEntity

@Dao
interface FeedbackDao {
    @Insert
    suspend fun insertFeedback(feedback: FeedbackEntity)

    @Query("SELECT * FROM feedbacks")
    suspend fun getAllFeedbacks(): List<FeedbackEntity>
}

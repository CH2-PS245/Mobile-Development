//package com.example.dishdash.data.Dao
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.example.dishdash.data.response.UserResponse
//
//
//@Dao
//interface UserDao {
//
//    //for single user insert
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertUser(userResponse : UserResponse) : Long
//
//    // for list of user insert
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertUserAll(userResponse: List<UserResponse>) : List<Long>
//
//    //Checking user exist or not in our db
//    @Query("SELECT * FROM UserResponse WHERE email LIKE :email AND password LIKE :password ")
//    fun readLoginData(email : String, password: String) : UserResponse
//
//    // getting user data details
//    @Query("SELECT * FROM userresponse where id LIKE :id")
//    fun getUserDataDetails(id:Int):UserResponse
//
//    //Deleting all user from db
//    @Query("DELETE FROM USERRESPONSE")
//    fun deleteAll()
//
//
//}
package com.example.dishdash.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.dishdash.data.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "session")
class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun saveSession(userModel: UserModel){
        dataStore.edit {
            it[EMAIL_KEY] = userModel.email
            it[ACCESS_TOKEN] = userModel.accessToken
            it[REFRESH_TOKEN] = userModel.refreshToken
            it[IS_LOGIN_KEY] = true
        }
    }
    fun getSession() : Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[EMAIL_KEY] ?: "",
                preferences[ACCESS_TOKEN] ?: "accessToken",
                preferences[REFRESH_TOKEN] ?: "refreshToken",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }
    suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
        INSTANCE
    }
    companion object {
        @Volatile
        private var INSTANCE : UserPreference? = null
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
        private val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")


        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this){
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
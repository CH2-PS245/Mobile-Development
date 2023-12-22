package com.example.dishdash.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dishdash.injection.Injection
import com.example.dishdash.repository.MyRepostiory
import com.example.dishdash.retrofit.ApiService
import com.example.dishdash.ui.viewModel.DetailRestauratViewModel
import com.example.dishdash.ui.viewModel.HomeViewModel
import com.example.dishdash.ui.viewModel.LoginViewModel
import com.example.dishdash.ui.viewModel.ProfileViewModel
import com.example.dishdash.ui.viewModel.RegisterViewModel
import com.example.dishdash.ui.viewModel.SharedViewModel

class ViewModelFactory(private val repository: MyRepostiory) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailRestauratViewModel::class.java) -> {
                DetailRestauratViewModel() as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel class : " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE : ViewModelFactory ?= null
        @JvmStatic
        fun getInstance(context: Context) : ViewModelFactory {
            if (INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
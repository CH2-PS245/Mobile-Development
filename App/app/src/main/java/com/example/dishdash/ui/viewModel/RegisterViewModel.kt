package com.example.dishdash.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dishdash.repository.MyRepostiory

class RegisterViewModel(private val myRepostiory: MyRepostiory) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun register(username:String,email:String,password:String) = myRepostiory.register(username,email, password)
}
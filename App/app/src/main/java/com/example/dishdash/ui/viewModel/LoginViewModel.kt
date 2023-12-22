package com.example.dishdash.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishdash.data.UserModel
import com.example.dishdash.repository.MyRepostiory
import kotlinx.coroutines.launch

class LoginViewModel(private val repostiory: MyRepostiory) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun saveSession(user: UserModel){
        viewModelScope.launch {
            repostiory.saveSession(user)
        }
    }
    fun login(email:String,password:String) = repostiory.login(email,password)
}
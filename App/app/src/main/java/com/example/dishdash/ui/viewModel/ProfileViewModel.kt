package com.example.dishdash.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishdash.data.response.Data
import com.example.dishdash.data.response.DataUser
import com.example.dishdash.repository.MyRepostiory
import kotlinx.coroutines.launch

class ProfileViewModel(private val repostiory: MyRepostiory) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail


    fun logout(){
        viewModelScope.launch {
            repostiory.logout()
        }
    }
}
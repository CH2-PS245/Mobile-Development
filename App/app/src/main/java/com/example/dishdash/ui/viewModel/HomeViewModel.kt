package com.example.dishdash.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dishdash.data.UserModel
import com.example.dishdash.data.response.FoodResponse
import com.example.dishdash.data.response.FoodResponseItem
import com.example.dishdash.repository.MyRepostiory
import com.example.dishdash.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: MyRepostiory) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _foods = MutableLiveData<List<FoodResponseItem>>()
    val foods: LiveData<List<FoodResponseItem>> get() = _foods



//    fun getFoods(query : String){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getFoods()
//        client.enqueue(object : Callback<FoodResponse>{
//            override fun onResponse(
//                call: Call<FoodResponse>,
//                response: Response<FoodResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful){
//                    val responseBody =response.body()
//                    if (responseBody != null){
//                        _foods.value = response.body()?.foodResponse
//                    }
//                } else {
//                    Log.e(TAG,"onFailure : ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }
//    fun getFoods(): LiveData<PagingData<FoodResponseItem>> = repository.getFoods().cachedIn(viewModelScope)
    fun getSession() : LiveData<UserModel>{
        return repository.getSession().asLiveData()
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
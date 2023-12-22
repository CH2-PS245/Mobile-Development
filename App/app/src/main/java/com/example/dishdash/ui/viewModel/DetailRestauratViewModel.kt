package com.example.dishdash.ui.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dishdash.data.response.RestoResponseItem
import com.example.dishdash.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRestauratViewModel : ViewModel() {
    private val _detailRestoList = MutableLiveData<List<RestoResponseItem>>()
    val detailRestoList: LiveData<List<RestoResponseItem>> = _detailRestoList

    fun getDetailResto() {
        val client = ApiConfig.getApiService().getDetailResto()
        client.enqueue(object : Callback<List<RestoResponseItem>> {
            override fun onResponse(
                call: Call<List<RestoResponseItem>>,
                response: Response<List<RestoResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _detailRestoList.value = response.body()
                } else {
                    Log.e("DetailViewModel", "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RestoResponseItem>>, t: Throwable) {
                // Handle failure here
                Log.e("DetailViewModel", "onFailure: ${t.message}")

            }
        })
    }
}

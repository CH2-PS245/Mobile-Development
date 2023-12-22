package com.example.dishdash.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dishdash.data.response.FoodResponseItem

class SharedViewModel : ViewModel() {
    private val _checkedItems = MutableLiveData<Set<FoodResponseItem>>()
    val checkedItems: LiveData<Set<FoodResponseItem>> get() = _checkedItems

    fun saveCheckedItems(items: Set<FoodResponseItem>) {
        _checkedItems.value = items
    }
}

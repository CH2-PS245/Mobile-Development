package com.example.dishdash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdash.adapter.FoodAdapter
import com.example.dishdash.data.response.FoodResponseItem
import com.example.dishdash.databinding.ActivityMoreFoodBinding
import com.example.dishdash.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoreFoodBinding
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var call: Call<List<FoodResponseItem>>
    private lateinit var originalData: List<FoodResponseItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoreFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor =  resources.getColor(R.color.white)
        window.navigationBarColor = resources.getColor(R.color.white)

        foodAdapter = FoodAdapter { foodresponse -> foodOnClick(foodresponse) }
        binding.rvMoreFood.adapter = foodAdapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMoreFood.layoutManager = layoutManager

        binding.searchIcon.setOnClickListener {
            performSearch(binding.searchBar.text.toString())
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Sebelum teks berubah
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Panggil fungsi pencarian dengan teks yang terkini
                performSearch(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        search()
        getFoodData()
        getOriginalData()
    }
    private fun foodOnClick(foodResponseItem: FoodResponseItem) {

    }

    private fun getOriginalData() {
        call = ApiConfig.getApiService().getFoods()
        call.enqueue(object : Callback<List<FoodResponseItem>> {
            override fun onResponse(
                call: Call<List<FoodResponseItem>>,
                response: Response<List<FoodResponseItem>>
            ) {
                if (response.isSuccessful) {
                    originalData = response.body() ?: emptyList()
                } else {
                    Toast.makeText(
                        this@MoreFoodActivity,
                        "Gagal Mendapatkan data awal",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<FoodResponseItem>>, t: Throwable) {
                Toast.makeText(this@MoreFoodActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun getFoodData() {
        call = ApiConfig.getApiService().getFoods()
        call.enqueue(object : Callback<List<FoodResponseItem>> {
            override fun onResponse(
                call: Call<List<FoodResponseItem>>,
                response: Response<List<FoodResponseItem>>
            ) {
                if (response.isSuccessful) {
                    foodAdapter.submitList(response.body())
                } else {
                    Toast.makeText(
                        this@MoreFoodActivity,
                        "Gagal Menampilkan data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<FoodResponseItem>>, t: Throwable) {
                Toast.makeText(this@MoreFoodActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun search() {
        binding.searchBar.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(binding.searchBar.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

    }

    private fun performSearch(query: String) {
        if (query.isNotEmpty()) {
            val filteredList = foodAdapter.currentList.filter { it.name?.contains(query, true) == true }
            foodAdapter.submitList(filteredList)

            if (filteredList.isEmpty()) {
                Toast.makeText(this@MoreFoodActivity, "Tidak ada hasil untuk \"$query\"", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Query is empty, return to the original data
            foodAdapter.submitList(originalData)
        }
    }

    companion object {
        const val EXTRA_FOOD_ITEM = "extra_food_item"
    }
}

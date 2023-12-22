package com.example.dishdash.ui.WelcomeActivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdash.R
import com.example.dishdash.adapter.FoodHistoryAdapter
import com.example.dishdash.data.response.FoodResponseItem
import com.example.dishdash.retrofit.ApiConfig
import com.example.dishdash.ui.viewModel.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatatMakananActivity : AppCompatActivity() {
    private lateinit var adapter: FoodHistoryAdapter
    private lateinit var call: Call<List<FoodResponseItem>>
    private val checkedItems = mutableSetOf<FoodResponseItem>()
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catat_makanan)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        val searchEditText: EditText = findViewById(R.id.searchEditText)
        val searchIcon: ImageView = findViewById(R.id.searchIcon)


        val backButton: ImageButton = findViewById(R.id.ib_back)
        backButton.setOnClickListener {
            finish()
        }

        // Atur OnClickListener untuk tombol pencarian
        searchIcon.setOnClickListener {
            performSearch(searchEditText.text.toString())
        }

        // Atur OnEditorActionListener untuk EditText agar dapat menangkap event "Enter"
        searchEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(searchEditText.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }



        // Move the RecyclerView initialization inside onCreate
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Set up the RecyclerView with FoodHistoryAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FoodHistoryAdapter { position ->
            // Handle item click if needed
            Toast.makeText(
                this@CatatMakananActivity,
                "Clicked on item at position $position",
                Toast.LENGTH_SHORT
            ).show()
        }
        recyclerView.adapter = adapter


        // Optional: Set an item click listener for the submit button
        val btnSubmit: Button = findViewById(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            // Menyimpan data yang dicentang ke SharedViewModel
            for (item in checkedItems) {
                // Save the checked state to SharedPreferences
                saveCheckedState(item)
                val checkedItems = // ambil set item yang dicentang
                    sharedViewModel.saveCheckedItems(checkedItems)
            }

            // Menampilkan pesan "makanan sudah tercatat"
            Toast.makeText(
                this@CatatMakananActivity,
                "Makanan sudah tercatat",
                Toast.LENGTH_SHORT
            ).show()
        }

        getFoodData()
    }

    private fun saveCheckedState(foodItem: FoodResponseItem) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Simpan ID atau informasi unik lainnya dari item makanan yang dicentang
        editor.putBoolean("foodId_${foodItem.id}", true)
        editor.apply()
    }

    private fun performSearch(query: String) {
        // Ambil data dari adapter
        val currentList = adapter.currentList

        // Lakukan filter berdasarkan nama
        val filteredList = currentList.filter { foodResponseItem ->
            foodResponseItem.name?.contains(query, true) == true
        }

        // Update data pada adapter dengan hasil filter
        adapter.submitList(filteredList)
    }
    private fun getFoodData() {
        call = ApiConfig.getApiService().getFoods()
        call.enqueue(object : Callback<List<FoodResponseItem>> {
            override fun onResponse(
                call: Call<List<FoodResponseItem>>,
                response: Response<List<FoodResponseItem>>
            ) {
                if (response.isSuccessful) {
                    adapter.submitList(response.body())
                } else {
                    Toast.makeText(
                        this@CatatMakananActivity,
                        "Gagal Menampilkan data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<FoodResponseItem>>, t: Throwable) {
                Toast.makeText(this@CatatMakananActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
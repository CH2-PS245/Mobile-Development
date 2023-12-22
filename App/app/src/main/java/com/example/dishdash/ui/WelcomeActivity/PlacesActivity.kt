package com.example.dishdash.ui.WelcomeActivity

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.dishdash.R
import com.example.dishdash.adapter.ViewPagerAdapter
import com.example.dishdash.databinding.ActivityPlacesBinding
import com.example.dishdash.model.DummyData
import com.example.dishdash.model.RestaurantModel
import com.google.android.material.tabs.TabLayoutMediator

class PlacesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacesBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var currentFragmentPosition: Int = 0
    private lateinit var searchView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val backButton: ImageButton = findViewById(R.id.back_btn)
        backButton.setOnClickListener {
            finish()
        }

        val imageView: ImageView = findViewById(R.id.img_places)
        val drawable = ContextCompat.getDrawable(this, R.drawable.img)
        val colorFilter = PorterDuffColorFilter(Color.parseColor("#14213D"), PorterDuff.Mode.SRC_IN)
        drawable?.colorFilter = colorFilter
        imageView.setImageDrawable(drawable)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        // Inisialisasi searchView
        searchView = findViewById(R.id.searchView)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Semua"
                    tab.icon = ContextCompat.getDrawable(this@PlacesActivity, R.drawable.ic_world)
                    val allRestaurants = DummyData.generateAllRestaurants()
                    viewPagerAdapter.updateFragmentData(position, allRestaurants)
                }
                1 -> {
                    tab.text = "Terdekat"
                    tab.icon = ContextCompat.getDrawable(this@PlacesActivity, R.drawable.ic_nearby)
                    val nearbyRestaurants = DummyData.generateNearbyRestaurants()
                    val filteredData = performSearch(nearbyRestaurants, searchView.text.toString())
                    viewPagerAdapter.updateFragmentData(position, filteredData)
                }
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }.attach()

        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Sebelum teks berubah
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Tidak perlu memanggil filterData() di sini karena sudah dipanggil pada afterTextChanged
            }

            override fun afterTextChanged(s: Editable?) {
                // Setelah teks berubah, panggil filterData()
                filterData(s.toString())
            }
        })
    }

    private fun filterData(query: String) {
        val allRestaurants = DummyData.generateAllRestaurants()
        val nearbyRestaurants = DummyData.generateNearbyRestaurants()

        when (currentFragmentPosition) {
            0 -> {
                val filteredData = performSearch(allRestaurants, query)
                viewPagerAdapter.updateFragmentData(currentFragmentPosition, filteredData)
            }
            1 -> {
                val filteredData = performSearch(nearbyRestaurants, query)
                viewPagerAdapter.updateFragmentData(currentFragmentPosition, filteredData)
            }
            else -> throw IllegalArgumentException("Invalid position: $currentFragmentPosition")
        }
    }

    private fun performSearch(data: List<RestaurantModel>, query: String): List<RestaurantModel> {
        return data.filter { it.name.contains(query, ignoreCase = true) }
    }
}
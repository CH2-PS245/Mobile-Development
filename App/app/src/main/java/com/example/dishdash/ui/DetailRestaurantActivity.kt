package com.example.dishdash.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dishdash.R
import com.example.dishdash.adapter.FoodRestoAdapter
import com.example.dishdash.data.ViewModelFactory
import com.example.dishdash.data.response.FoodResponseItem
import com.example.dishdash.data.response.RestoResponseItem
import com.example.dishdash.databinding.ActivityDetailRestaurantBinding
import com.example.dishdash.retrofit.ApiConfig
import com.example.dishdash.ui.viewModel.DetailRestauratViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRestaurantActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityDetailRestaurantBinding
    private lateinit var mMap: GoogleMap
    private lateinit var call: Call<List<RestoResponseItem>>
    private lateinit var foodRestoAdapter: FoodRestoAdapter
    private lateinit var constraintLayout : ConstraintLayout

    private val viewModel by viewModels<DetailRestauratViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        constraintLayout = binding.constraintLayout

        foodRestoAdapter =  FoodRestoAdapter()
        binding.rvMenu.adapter = foodRestoAdapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvMenu.layoutManager = layoutManager


        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val name = intent.getStringExtra("name")
        if (name != null){
            viewModel.getDetailResto()
        }
        viewModel.detailRestoList.observe(this){ detailResto ->
            if (detailResto.isNotEmpty()) {
                setDetailResto(detailResto[0]) // Assuming you want to handle the first item
            }
        }
        getFoodResto()
    }

    private fun setDetailResto(detailResto: RestoResponseItem){
        binding.apply {
            tvNameRestaurant.text = detailResto.name
            ratingBar.rating = detailResto.rating!!.toFloat()
        }
        Glide.with(this)
            .load(detailResto.photoUrlResto)
            .centerCrop()
            .into(binding.ivRestaurant)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("MapDebug", "onMapReady called")
        mMap = googleMap

        //Marker
        mMap.uiSettings.apply {
            isMapToolbarEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isZoomControlsEnabled = true
        }

        val latLng = LatLng(-6.2088, 106.8456)
        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Jakarta")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        binding.btnOpenGmap.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${latLng.latitude},${latLng.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                // Jika tidak ada aplikasi Google Maps yang terinstall, beri tahu pengguna
                Toast.makeText(this, "Aplikasi Google Maps tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getFoodResto(){
        call = ApiConfig.getApiService().getFoodResto()
        call.enqueue(object : Callback<List<RestoResponseItem>>{
            override fun onResponse(
                call: Call<List<RestoResponseItem>>,
                response: Response<List<RestoResponseItem>>
            ) {
                if (response.isSuccessful){
                    foodRestoAdapter.submitList(response.body())
                    foodRestoAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@DetailRestaurantActivity,"Gagal Menampilkan data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RestoResponseItem>>, t: Throwable) {
                Toast.makeText(this@DetailRestaurantActivity,t.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        })
    }

}

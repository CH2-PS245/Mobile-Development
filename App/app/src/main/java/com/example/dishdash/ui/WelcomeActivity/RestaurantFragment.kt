package com.example.dishdash.ui.WelcomeActivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdash.R
import com.example.dishdash.adapter.RestaurantAdapter
import com.example.dishdash.adapter.RestoAdapter
import com.example.dishdash.data.response.RestoResponseItem
import com.example.dishdash.databinding.FragmentHomeBinding
import com.example.dishdash.databinding.FragmentRestaurantBinding
import com.example.dishdash.model.DummyData
import com.example.dishdash.model.RestaurantModel
import com.example.dishdash.retrofit.ApiConfig
import com.example.dishdash.ui.DetailRestaurantActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantFragment : Fragment() {

    private lateinit var binding : FragmentRestaurantBinding
    private lateinit var restoAdapter: RestoAdapter
    private lateinit var call: Call<List<RestoResponseItem>>
    private lateinit var constraintLayout: ConstraintLayout


    companion object {
        const val IS_ALL_RESTAURANTS_FRAGMENT = "isAllRestaurantsFragment"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        val view = binding.root
        constraintLayout = binding.constraintLayout
        restoAdapter = RestoAdapter { restoResponse -> restoOnClick(restoResponse) }
        binding.rvU.adapter = restoAdapter
        val layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL, false)
        binding.rvU.layoutManager = layoutManager
        return view
    }

    private fun restoOnClick(restoResponse: RestoResponseItem) {
        val intent = Intent(context, DetailRestaurantActivity::class.java)
        intent.putExtra("restoId", restoResponse.id) // Ganti dengan parameter yang sesuai
        intent.putExtra("photoUrl", restoResponse.photoUrlResto)
        intent.putExtra("name", restoResponse.name)
        intent.putExtra("rating", restoResponse.rating)
        intent.putExtra("latlong", restoResponse.latlong.toString())
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isAllRestaurantsFragment = arguments?.getBoolean(IS_ALL_RESTAURANTS_FRAGMENT) ?: false

        if (isAllRestaurantsFragment) {
            getResto()
        } else {
            getResto()
        }
    }


    fun getResto(){
        call = ApiConfig.getApiService().getResto()
        call.enqueue(object : Callback<List<RestoResponseItem>>{
            override fun onResponse(
                call: Call<List<RestoResponseItem>>,
                response: Response<List<RestoResponseItem>>
            ) {
                if (response.isSuccessful){
                    restoAdapter.submitList(response.body())
                    restoAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context,"Gagal Menampilkan data", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<List<RestoResponseItem>>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage, Toast.LENGTH_SHORT).show()

            }

        })
    }


    fun updateRestaurantList(restaurants: List<RestaurantModel>) {
        if (::restaurantAdapter.isInitialized) {
            restaurantAdapter.updateData(restaurants)
        }
    }
}

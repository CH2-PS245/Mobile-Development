package com.example.dishdash.ui

import CustomCircularProgressIndicator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishdash.MoreFoodActivity
import com.example.dishdash.adapter.FoodAdapter
import com.example.dishdash.data.ViewModelFactory
import com.example.dishdash.data.response.FoodResponseItem
import com.example.dishdash.databinding.FragmentHomeBinding
import com.example.dishdash.retrofit.ApiConfig
import com.example.dishdash.ui.Jetpack.CustomRoundedHorizontalProgressIndicator
import com.example.dishdash.ui.Jetpack.navy
import com.example.dishdash.ui.Jetpack.navyLight
import com.example.dishdash.ui.WelcomeActivity.CatatMakananActivity
import com.example.dishdash.ui.WelcomeActivity.PlacesActivity
import com.example.dishdash.ui.WelcomeActivity.WelcomeActivity
import com.example.dishdash.ui.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var call: Call<List<FoodResponseItem>>
    private lateinit var constraintLayout : ConstraintLayout

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        constraintLayout = binding.constraintLayout

        //Replace text day with system
        val dayOfWeek = getDayOfWeek()
        binding.day.text =dayOfWeek


        val composeViewCarbo : ComposeView = binding.circularComposeCarbo
        val composeViewProtein : ComposeView = binding.circularComposeProtein
        val composeViewFat : ComposeView = binding.circularComposeFat

        val composeViewHorizontal : ComposeView = binding.horizontalPercentage


        foodAdapter = FoodAdapter { foodresponse -> foodOnClick(foodresponse) }
        binding.rvFood.adapter = foodAdapter
        val layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvFood.layoutManager = layoutManager

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            val accessToken = user.accessToken
            val refreshToken = user.refreshToken
            if (!user.isLogin) {
                startActivity(Intent(context, WelcomeActivity::class.java))
                requireActivity().finish()
            }
//            viewModel.getFoods().observe(viewLifecycleOwner) { result ->
//                foodAdapter.submitData(lifecycle, result)
//            }
        }




        val carbo = 60
        val protein = 100
        val fat = 129
        val calorie = 99
        binding.totalCalori.text = (100-calorie).toString()
        binding.totalCaloriConsume.text = calorie.toString()





            composeViewCarbo.setContent {
            CustomCircularProgressIndicator(
                modifier = Modifier,
                initialValue = carbo,
                primaryColor = navy,
                secondaryColor = navyLight,
                circleRadius = 125f,
                type = "Carbo",
                onPositionChange = {

                }
            )
        }
        composeViewProtein.setContent {
            CustomCircularProgressIndicator(
                modifier = Modifier,
                initialValue = protein,
                primaryColor = navy,
                secondaryColor = navyLight,
                circleRadius = 125f,
                type = "Protein",
                onPositionChange = {

                }
            )
        }
        composeViewFat.setContent {
            CustomCircularProgressIndicator(
                modifier = Modifier,
                initialValue = fat,
                primaryColor = navy,
                secondaryColor = navyLight,
                circleRadius = 125f,
                type = "Fat",
                onPositionChange = {

                }
            )
        }
        composeViewHorizontal.setContent {
            CustomRoundedHorizontalProgressIndicator(
                modifier = Modifier
                    .width(300.dp)
                    .height(20.dp),
                initialValue = calorie,
                primaryColor = navy,
                secondaryColor = navyLight,
                onPositionChange = {

                }
            )
        }



        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(context, WelcomeActivity::class.java))
                requireActivity().finish()
            }
        }

        binding.btnFoodRecord.setOnClickListener {
            startActivity(Intent(requireContext(), CatatMakananActivity::class.java))
        }
        binding.btnSearchLocation.setOnClickListener {
            startActivity(Intent(requireContext(), PlacesActivity::class.java))
        }
        binding.btnReadMore.setOnClickListener {restoResponse ->
            startActivity(Intent(context,MoreFoodActivity::class.java))
        }
        getFoodData()
        return view
    }

//    private fun readMore(foodResponseItem: FoodResponseItem){
//        val intent = Intent(context,MoreFoodActivity::class.java)
//        intent.putExtra("name",foodResponseItem.name)
//        intent.putExtra("photoUrl",foodResponseItem.photoUrl)
//        intent.putExtra("calorire",foodResponseItem.calorie)
//        startActivity(intent)
//    }

    private fun getDayOfWeek(): String {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // Menggunakan SimpleDateFormat untuk mendapatkan nama hari dalam bahasa Indonesia
        val sdf = SimpleDateFormat("EEEE", Locale("id", "ID"))
        return sdf.format(calendar.time)
    }


    private fun foodOnClick(foodResponseItem: FoodResponseItem) {

    }

    private fun getFoodData() {
        call = ApiConfig.getApiService().getFoods()
        call.enqueue(object : Callback<List<FoodResponseItem>>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<FoodResponseItem>>, response: Response<List<FoodResponseItem>>) {
                if (response.isSuccessful){
                    foodAdapter.submitList(response.body())
                    foodAdapter.notifyDataSetChanged()
                } else{
                    Log.e("HomeFragment", "Error")
                }
            }

            override fun onFailure(call: Call<List<FoodResponseItem>>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage,Toast.LENGTH_SHORT).show()
            }

        })

    }


}

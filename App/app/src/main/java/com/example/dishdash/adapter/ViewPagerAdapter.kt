package com.example.dishdash.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dishdash.model.RestaurantModel
import com.example.dishdash.ui.WelcomeActivity.RestaurantFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val allRestaurantsFragment: RestaurantFragment = RestaurantFragment().apply {
        arguments = Bundle().apply {
            putBoolean(RestaurantFragment.IS_ALL_RESTAURANTS_FRAGMENT, true)
        }
    }

    private val nearbyRestaurantsFragment: RestaurantFragment = RestaurantFragment().apply {
        arguments = Bundle().apply {
            putBoolean(RestaurantFragment.IS_ALL_RESTAURANTS_FRAGMENT, false)
        }
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> allRestaurantsFragment
            1 -> nearbyRestaurantsFragment
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    fun updateFragmentData(position: Int, data: List<RestaurantModel>) {
        when (position) {
            0 -> allRestaurantsFragment.updateRestaurantList(data)
            1 -> nearbyRestaurantsFragment.updateRestaurantList(data)
        }
    }
}

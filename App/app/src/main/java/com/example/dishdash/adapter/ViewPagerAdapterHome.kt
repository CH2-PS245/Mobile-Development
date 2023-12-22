package com.example.dishdash.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dishdash.ui.FavoriteFragment
import com.example.dishdash.ui.HistoryFragment
import com.example.dishdash.ui.HomeFragment
import com.example.dishdash.ui.ProfileFragment

class ViewPagerAdapterHome(activity : AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 4
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> HomeFragment()
            1 -> FavoriteFragment()
            2 -> HistoryFragment()
            3 -> ProfileFragment()
            else -> {throw IllegalArgumentException("Invalid position: $position")}
        }
    }
}
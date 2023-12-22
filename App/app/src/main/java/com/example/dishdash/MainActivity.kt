package com.example.dishdash

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.dishdash.adapter.ViewPagerAdapterHome
import com.example.dishdash.databinding.ActivityMainBinding
import com.example.dishdash.ui.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        val viewPagerAdapter = ViewPagerAdapterHome(this)
        binding.viewPager.adapter = viewPagerAdapter



        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNav.menu.getItem(position).isChecked = true
            }
        })

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home, R.id.nav_favorite, R.id.nav_history, R.id.nav_profile -> {
                    binding.viewPager.currentItem = getSelectedItemIndex(it.itemId)
                    true
                }
                else -> false
            }
        }
    }

    private fun getSelectedItemIndex(itemId: Int): Int {
        return when (itemId) {
            R.id.nav_home -> 0
            R.id.nav_favorite -> 1
            R.id.nav_history -> 2
            R.id.nav_profile -> 3
            else -> 0
        }
    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment,fragment)
        fragmentTransaction.commit()
    }
}

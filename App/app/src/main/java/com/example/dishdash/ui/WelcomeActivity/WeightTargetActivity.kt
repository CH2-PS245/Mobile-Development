package com.example.dishdash.ui.WelcomeActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dishdash.R
import com.example.dishdash.databinding.ActivityWeightTargetBinding

class WeightTargetActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWeightTargetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeightTargetBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setupAction()
    }

    private fun setupAction() {
        binding.btnNext.setOnClickListener {

            startActivity(Intent(this,HoursSleepActivity::class.java))
        }
    }
}
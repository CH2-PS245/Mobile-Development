package com.example.dishdash.ui.WelcomeActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dishdash.R
import com.example.dishdash.databinding.ActivityHeightBinding

class HeightActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHeightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeightBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupAction()
    }
    private fun setupAction(){
        binding.btnNext.setOnClickListener {

            startActivity(Intent(this,WeightTargetActivity::class.java))
        }
    }
}
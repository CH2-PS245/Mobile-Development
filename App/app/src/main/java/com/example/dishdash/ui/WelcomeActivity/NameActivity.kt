package com.example.dishdash.ui.WelcomeActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dishdash.R
import com.example.dishdash.databinding.ActivityNameBinding

class NameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupAction()
    }

    fun setupAction(){
        val btnSave = binding.btnNext
        btnSave.setOnClickListener {
            startActivity(Intent(this, DatePickerActivity::class.java))
        }
    }
}
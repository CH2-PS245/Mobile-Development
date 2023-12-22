package com.example.dishdash.ui.WelcomeActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.dishdash.R
import com.example.dishdash.databinding.ActivityHoursSleepBinding

class HoursSleepActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHoursSleepBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHoursSleepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinnerHours = binding.spinnerHours

        // Membuat daftar angka dari 1 hingga 24
        val hoursList = (1..24).map { it.toString() }

        // Membuat adapter untuk Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hoursList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Menetapkan adapter ke Spinner
        spinnerHours.adapter = adapter



        binding.btnNext.setOnClickListener {

            startActivity(Intent(this,MostActiveActivity::class.java))
        }
    }
}
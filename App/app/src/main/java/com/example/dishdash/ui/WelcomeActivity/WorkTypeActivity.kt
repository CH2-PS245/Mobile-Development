package com.example.dishdash.ui.WelcomeActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.example.dishdash.R

class WorkTypeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_type)

        val edHoursWork = findViewById<EditText>(R.id.ed_hours_work)
        val btnReduce = findViewById<Button>(R.id.btn_reduce)
        val btnIncrease = findViewById<Button>(R.id.btn_increase)

        btnReduce.setOnClickListener {
            // Kurangi nilai jika nilai saat ini lebih besar dari 1
            val currentValue = edHoursWork.text.toString().toIntOrNull() ?: 1
            if (currentValue > 0) {
                edHoursWork.setText((currentValue - 1).toString())
            }
        }

        btnIncrease.setOnClickListener {
            // Tambahkan nilai jika nilai saat ini kurang dari 24
            val currentValue = edHoursWork.text.toString().toIntOrNull() ?: 1
            if (currentValue < 24) {
                edHoursWork.setText((currentValue + 1).toString())
            }
        }

        edHoursWork.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Mendapatkan nilai saat ini
                val value = s.toString().toIntOrNull() ?: 0

                // Memastikan nilai tidak melebihi 24
                if (value > 24) {
                    edHoursWork.setText("24")
                }
            }
        })
    }
}
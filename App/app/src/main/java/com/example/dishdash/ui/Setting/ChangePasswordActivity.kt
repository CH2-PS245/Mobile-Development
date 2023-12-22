package com.example.dishdash.ui.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.dishdash.R

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val backButton: ImageButton = findViewById(R.id.im_back_pass)
        backButton.setOnClickListener {
            finish()
        }
    }
}
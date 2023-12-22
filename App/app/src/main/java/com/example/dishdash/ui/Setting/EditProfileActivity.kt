package com.example.dishdash.ui.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.dishdash.R

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val backButton: ImageButton = findViewById(R.id.im_back_edit)
        backButton.setOnClickListener {
            finish()
        }
    }
}
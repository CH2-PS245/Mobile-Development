package com.example.dishdash.ui.Setting

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import com.example.dishdash.R
import com.example.dishdash.data.Dao.FeedbackDao
import com.example.dishdash.database.AppDatabase
import com.example.dishdash.database.FeedbackEntity
import com.example.dishdash.databinding.ActivityFeedbackBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedbackActivity : AppCompatActivity() {

    private lateinit var contactInfoEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var approvalCheckBox: CheckBox
    private lateinit var btnPlus: ImageButton
    private var selectedImageUri: Uri? = null
    private lateinit var feedbackDao: FeedbackDao
    private lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_feedback)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "feedback-database"
        ).build()

        feedbackDao = db.feedbackDao()

        contactInfoEditText = findViewById(R.id.edit_text_email_feed)
        descriptionEditText = findViewById(R.id.edit_text_descriptionfeed)
        approvalCheckBox = findViewById(R.id.checkbox_feed)
        val submitButton: Button = findViewById(R.id.btn_submit)
        btnPlus = findViewById(R.id.btn_plus)

        btnPlus.setOnClickListener {
            openImagePicker()
        }

        submitButton.setOnClickListener {
            if (validateInput()) {
                val contactInfo = contactInfoEditText.text.toString()
                val description = descriptionEditText.text.toString()

                val feedback = FeedbackEntity(contactInfo = contactInfo, description = description, imageUri = selectedImageUri.toString())
                insertFeedback(feedback)

                displayData(contactInfo, description, imageUri = null)
            }
        }

        val backButton: ImageButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun insertFeedback(feedback: FeedbackEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            feedbackDao.insertFeedback(feedback)

            // Tambahkan log jika data berhasil disimpan♦
            Log.d("FeedbackActivity", "Data berhasil disimpan ke database: $feedback")
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data

            }
        }

    private fun validateInput(): Boolean {
        val contactInfo = contactInfoEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()
        val isApproved = approvalCheckBox.isChecked

        if (contactInfo.isEmpty() || description.length < 10) {
            Toast.makeText(this, "Mohon isi data dengan benar", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isApproved) {
            Toast.makeText(this, "Anda harus menyetujui persyaratan", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun displayData(contactInfo: String, description: String, imageUri: Uri?) {
        Toast.makeText(this, "Contact Info: $contactInfo\nDescription: $description", Toast.LENGTH_LONG).show()

        if (imageUri != null) {
            // Proses imageUri di sini
        }
    }
}

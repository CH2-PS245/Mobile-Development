package com.example.dishdash.ui


import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdash.R
import com.example.dishdash.adapter.FoodHistoryAdapter
import com.example.dishdash.data.response.FoodResponseItem
import com.example.dishdash.databinding.FragmentHistoryBinding
import com.example.dishdash.ui.viewModel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Locale



@RequiresApi(Build.VERSION_CODES.N)
class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val myCalendar = Calendar.getInstance()
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: FoodHistoryAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]


        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Dapatkan data dari SharedPreferences dan tampilkan di RecyclerView
        val checkedItems = mutableListOf<FoodResponseItem>()

        // Gantilah 10 dengan jumlah item makanan yang mungkin Anda miliki
        for (i in 1..10) {
            val isChecked = sharedPreferences.getBoolean("foodId_$i", false)

            if (isChecked) {
                // Ambil informasi item makanan dari sumber data Anda (misalnya, API atau DB)
                // dan tambahkan ke daftar checkedItems
                val foodItem = getFoodItemById(i)
                foodItem?.let {
                    checkedItems.add(it)
                }
            }
        }

        // Inisialisasi RecyclerView dan adapter
        recyclerView = view.findViewById(R.id.rv_history_menu)
        adapter = FoodHistoryAdapter { position ->
            // Handle item click if needed
            Toast.makeText(
                requireContext(),
                "Clicked on item at position $position",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Atur RecyclerView dengan layout manager dan adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Update RecyclerView dengan data dari checkedItems
        adapter.submitList(checkedItems)

        // Menerapkan observer untuk memperbarui RecyclerView saat data berubah
        sharedViewModel.checkedItems.observe(viewLifecycleOwner, Observer { checkedItems ->
            Log.d("HistoryFragment", "Observer called with $checkedItems")
            // Mengonversi ke List<FoodResponseItem> atau tipe data yang diharapkan oleh adapter
            val foodResponseItems = checkedItems.map { checkedItem ->
                FoodResponseItem(
                    id = checkedItem.id,
                    name = checkedItem.name,
                    calorie = checkedItem.calorie
                )
            }
            Log.d("HistoryFragment", "FoodResponseItems: $foodResponseItems")
            // Update RecyclerView dengan data baru
            adapter.submitList(foodResponseItems)
        })

        val btnDatePicker = binding.datePiker
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val todayDate = dateFormat.format(myCalendar.time)
        btnDatePicker.text = todayDate

        btnDatePicker.setOnClickListener {
            showDatePicker()
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                // Do something with the formatted date, for example, update a TextView
                binding.datePiker.text = formattedDate
            },
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        // Membatasi input tanggal
        val minDate = Calendar.getInstance()
        minDate.add(Calendar.DAY_OF_MONTH, -6)
        datePickerDialog.datePicker.minDate = minDate.timeInMillis

        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.DAY_OF_MONTH, 0)
        datePickerDialog.datePicker.maxDate = maxDate.timeInMillis
        datePickerDialog.show()
    }

    private fun getFoodItemById(foodId: Int): FoodResponseItem? {
        // Implementasi untuk mendapatkan informasi item makanan berdasarkan ID
        // Anda dapat mengambil data dari sumber data seperti API, Database, dll.
        // Contoh sederhana:
        return FoodResponseItem(
            id = foodId,
            name = "Food $foodId",
            calorie = 100  // Gantilah ini dengan informasi sesuai kebutuhan Anda
        )
    }
}

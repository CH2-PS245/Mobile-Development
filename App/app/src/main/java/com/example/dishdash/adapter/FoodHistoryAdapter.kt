package com.example.dishdash.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.dishdash.data.response.FoodResponseItem
import com.example.dishdash.databinding.ListItemCatatMakananBinding

class FoodHistoryAdapter(private val onItemClick: (FoodResponseItem) -> Unit) :
    ListAdapter<FoodResponseItem, FoodHistoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    private val checkedItems = mutableSetOf<FoodResponseItem>()


    class ViewHolder(private val binding: ListItemCatatMakananBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(foodResponseItem: FoodResponseItem, onItemClick: (FoodResponseItem) -> Unit) {
            val checkedItems = mutableSetOf<FoodResponseItem>()
            binding.apply {
                tvNameFood.text = foodResponseItem.name
                tvCalori.text = "Kalori: ${foodResponseItem.calorie} kalori"

                Glide.with(itemView)
                    .load(foodResponseItem.photoUrl)
                    .centerCrop()
                    .into(ivFood)

                btnCheckbox.isChecked = foodResponseItem in checkedItems

                root.setOnClickListener {
                    onItemClick(foodResponseItem)
                }

                btnCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        checkedItems.add(foodResponseItem)
                    } else {
                        checkedItems.remove(foodResponseItem)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemCatatMakananBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    fun getCheckedItems(): Set<FoodResponseItem> {
        return checkedItems
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FoodResponseItem>() {
            override fun areItemsTheSame(
                oldItem: FoodResponseItem,
                newItem: FoodResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FoodResponseItem,
                newItem: FoodResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

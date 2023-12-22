package com.example.dishdash.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishdash.R
import com.example.dishdash.data.response.FoodResponseItem

class FoodAdapter(private val onClick: (FoodResponseItem) -> Unit) : ListAdapter<FoodResponseItem, FoodAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val photoUrl: ImageView = itemView.findViewById(R.id.iv_food)
        private val name : TextView = itemView.findViewById(R.id.tv_name_food)
        private val calorie : TextView = itemView.findViewById(R.id.tv_calori)
        private val btnLike : ImageButton = itemView.findViewById(R.id.btn_like)
        private var currentFood: FoodResponseItem? = null
        private var isLike = false

        init {
            btnLike.setOnClickListener {
                currentFood?.let{
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(foodResponseItem: FoodResponseItem){
            currentFood  = foodResponseItem

            name.text = foodResponseItem.name
            calorie.text = "Kalori : ${foodResponseItem.calorie.toString()}"
            Glide.with(itemView).load(foodResponseItem.photoUrl)
                .centerCrop()
                .into(photoUrl)
            btnLike.setOnClickListener {
                isLike = !isLike
                fillColor(isLike)
            }

        }
        private fun fillColor(isLike : Boolean){
            btnLike.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    if (isLike) R.drawable.ic_favorite_full else R.drawable.ic_favorite
                )
            )
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food_like_200dp,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodAdapter.ViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }
    object DIFF_CALLBACK : DiffUtil.ItemCallback<FoodResponseItem>(){
        override fun areItemsTheSame(
            oldItem: FoodResponseItem,
            newItem: FoodResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: FoodResponseItem,
            newItem: FoodResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    }

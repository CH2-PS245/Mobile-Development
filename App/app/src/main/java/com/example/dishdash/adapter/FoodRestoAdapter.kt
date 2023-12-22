package com.example.dishdash.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishdash.R
import com.example.dishdash.data.response.RestoResponseItem

class FoodRestoAdapter() : ListAdapter<RestoResponseItem, FoodRestoAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val photoUrlFood: ImageView = itemView.findViewById(R.id.iv_food)
        private val nameFood : TextView = itemView.findViewById(R.id.tv_name_food)
        private val price : TextView = itemView.findViewById(R.id.tv_price_food)
        private var currentFood : RestoResponseItem? = null

        fun bind(foodResponseItem: RestoResponseItem){
            currentFood = foodResponseItem
            nameFood.text = foodResponseItem.name
            price.text = foodResponseItem.price
            Glide.with(itemView).load(foodResponseItem.photoUrlFood)
                .centerCrop()
                .into(photoUrlFood)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = getItem(position)
        holder.bind(food)
    }
    object DIFF_CALLBACK : DiffUtil.ItemCallback<RestoResponseItem>(){
        override fun areItemsTheSame(
            oldItem: RestoResponseItem,
            newItem: RestoResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: RestoResponseItem,
            newItem: RestoResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }
}
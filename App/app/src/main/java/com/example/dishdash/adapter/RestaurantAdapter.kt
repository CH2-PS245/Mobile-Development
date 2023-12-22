package com.example.dishdash.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdash.R
import com.example.dishdash.model.RestaurantModel


class RestaurantAdapter(private var restaurants: List<RestaurantModel>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tv_name_restaurant)
//        val descriptionTextView: TextView = itemView.findViewById(R.id.tv_desc)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
        val imageView: ImageView = itemView.findViewById(R.id.iv_restaurant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.nameTextView.text = restaurant.name
//        holder.descriptionTextView.text = restaurant.description
        holder.ratingBar.rating = restaurant.rating
        holder.imageView.setImageResource(restaurant.imageResId)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<RestaurantModel>) {
        restaurants = newData
        notifyDataSetChanged()
    }
}


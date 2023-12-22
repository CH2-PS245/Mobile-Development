package com.example.dishdash.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishdash.R
import com.example.dishdash.data.response.RestoResponseItem

class RestoAdapter(private val onClick: (RestoResponseItem) -> Unit) : ListAdapter<RestoResponseItem, RestoAdapter.ViewHolder>(DIFF_CALLBACK){


    class ViewHolder(itemView: View, val onClick: (RestoResponseItem) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val photoUrlResto : ImageView = itemView.findViewById(R.id.iv_restaurant)
        private val name : TextView = itemView.findViewById(R.id.tv_name_restaurant)
        private val rating : RatingBar = itemView.findViewById(R.id.rating_bar)
        private var currentResto: RestoResponseItem? = null


        @SuppressLint("SetTextI18n")
        fun bind(restoResponseItem: RestoResponseItem){
            currentResto =  restoResponseItem
            name.text = restoResponseItem.name
            rating.rating = restoResponseItem.rating!!.toFloat()
            Glide.with(itemView).load(restoResponseItem.photoUrlResto)
                .centerCrop()
                .into(photoUrlResto)
        }

        init {
            itemView.setOnClickListener {
                currentResto?.let{
                    onClick(it)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant,parent,false)

        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resto = getItem(position)
        holder.bind(resto)
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

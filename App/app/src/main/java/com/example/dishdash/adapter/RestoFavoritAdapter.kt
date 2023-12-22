package com.example.dishdash.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishdash.database.RestoEntity
import com.example.dishdash.databinding.ItemRestaurantBinding
import com.example.dishdash.ui.DetailRestaurantActivity


class RestoFavoritAdapter() : ListAdapter<RestoEntity, RestoFavoritAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestoFavoritAdapter.MyViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestoFavoritAdapter.MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class MyViewHolder(val binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(resto : RestoEntity){
            binding.tvNameRestaurant.text = resto.name
            Glide.with(binding.root.context)
                .load(resto.photoUrl)
                .into(binding.ivRestaurant)

            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailRestaurantActivity::class.java)
                intentDetail.putExtra("name", resto.name)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }
    companion object{
        val DIFF_CALLBACK = object  : DiffUtil.ItemCallback<RestoEntity>(){
            override fun areItemsTheSame(oldItem: RestoEntity, newItem: RestoEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RestoEntity, newItem: RestoEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}
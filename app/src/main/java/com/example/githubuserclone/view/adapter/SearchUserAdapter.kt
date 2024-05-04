package com.example.githubuserclone.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserclone.data.response.ItemsUser
import com.example.githubuserclone.databinding.ItemUserBinding


class SearchUserAdapter: ListAdapter<ItemsUser, SearchUserAdapter.MyViewholder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsUser)
    }

    class MyViewholder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsUser){
            binding.Name.text = user.login
            Glide.with(itemView.context).load(user.avatarUrl).into(binding.tvPhoto)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val user = getItem(position)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(getItem(holder.adapterPosition)) }
        holder.bind(user)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsUser>() {
            override fun areItemsTheSame(oldItem: ItemsUser, newItem: ItemsUser): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsUser, newItem: ItemsUser): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.example.githubuserclone.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserclone.data.response.GithubUserRepoResponse
import com.example.githubuserclone.data.response.ItemsItem
import com.example.githubuserclone.databinding.ItemReposBinding
import java.text.SimpleDateFormat
import java.util.Locale


class SearchRepoAdapter: ListAdapter<ItemsItem, SearchRepoAdapter.MyViewholder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    class MyViewholder(private val binding: ItemReposBinding): RecyclerView.ViewHolder(binding.root) {
        private var inputFormat: SimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        private var outputFormat: SimpleDateFormat =
            SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.getDefault())

        fun bind(repos: ItemsItem){
            binding.repoName.text = repos.fullName
            binding.repoDesc.text = repos.description
            binding.repoLanguage.text = repos.language
            binding.repoWatch.text = repos.watchers.toString()
            val date = repos.updatedAt?.let { inputFormat.parse(it) }
            val formatDate = date?.let { outputFormat.format(it) }
            binding.repoUpdate.text = formatDate
            Glide.with(itemView.context)
                .load(repos.owner?.avatarUrl)
                .into(binding.tvPhoto)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val binding = ItemReposBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
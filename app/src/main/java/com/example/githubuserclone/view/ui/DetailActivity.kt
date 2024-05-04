package com.example.githubuserclone.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.githubuserclone.R
import com.example.githubuserclone.data.response.GithubUserRepoResponse
import com.example.githubuserclone.data.response.ItemsItem
import com.example.githubuserclone.data.response.ItemsUser
import com.example.githubuserclone.databinding.ActivityDetailBinding
import com.example.githubuserclone.view.adapter.SearchRepoAdapter
import com.example.githubuserclone.view.adapter.SearchUserAdapter
import com.example.githubuserclone.view.model.DetailUserViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailUserViewModel>()

    companion object{
        val USERNAME = "username"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.appBar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager

        val username = intent.getStringExtra(USERNAME)
        detailViewModel.getDetailUser(username.toString())

        detailViewModel.userDetail.observe(this) {
            binding.tvNama.text = it.name
            binding.tvUsername.text = it.login
            if (it.bio == null){
                binding.tvDesc.text = "No Bio"
            }else{
                binding.tvDesc.text = it.bio.toString()
            }
            binding.location.text = it.location
            binding.tvRepository.text = "${it.publicRepos}"
            binding.tvFollower.text = "${it.followers}"
            binding.tvFollowing.text = "${it.following}"
            Glide.with(this@DetailActivity)
                .load(it.avatarUrl)
                .circleCrop()
                .into(binding.tvPhoto)
        }
        detailViewModel.getDetailUserRepos(username.toString())
        detailViewModel.userDetailRepo.observe(this){
            setRepoData(it)
        }

        binding.buttonRepo.setOnClickListener{
            detailViewModel.getDetailUserRepos(username.toString())
            detailViewModel.userDetailRepo.observe(this){
                setRepoData(it)
            }
        }
        binding.buttonFollower.setOnClickListener{
            detailViewModel.getDetailFollower(username.toString())
            detailViewModel.userDetailFollower.observe(this){
                setUserData(it)
            }
        }
        binding.buttonFollowing.setOnClickListener {
            detailViewModel.getDetailFollowing(username.toString())
            detailViewModel.userDetailFollowing.observe(this) {
                setUserData(it)
            }
        }
    }

    private fun setRepoData(it: List<ItemsItem>){
        val adapter = SearchRepoAdapter()
        adapter.submitList(it)
        binding.rvList.adapter = adapter

        adapter.setOnItemClickCallback(object : SearchRepoAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(data.htmlUrl)
                startActivity(intent)
            }
        })
    }

    private fun setUserData(it: List<ItemsUser?>?){
        val adapter = SearchUserAdapter()
        adapter.submitList(it)
        binding.rvList.adapter = adapter

        adapter.setOnItemClickCallback(object : SearchUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsUser) {
                val intent = Intent(this@DetailActivity, DetailActivity::class.java)
                intent.putExtra(USERNAME, data.login)
                startActivity(intent)
            }
        })
    }
}
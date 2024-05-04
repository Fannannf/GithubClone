package com.example.githubuserclone.view.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.githubuserclone.R
import com.example.githubuserclone.R.drawable.repo_fill_icon
import com.example.githubuserclone.R.drawable.repo_icon
import com.example.githubuserclone.R.drawable.user_fill_icon
import com.example.githubuserclone.R.drawable.user_icon
import com.example.githubuserclone.data.response.ItemsItem
import com.example.githubuserclone.data.response.ItemsUser
import com.example.githubuserclone.databinding.ActivityMainBinding
import com.example.githubuserclone.view.adapter.SearchRepoAdapter
import com.example.githubuserclone.view.adapter.SearchUserAdapter
import com.example.githubuserclone.view.model.SearchRepoViewModel
import com.example.githubuserclone.view.model.SearchUserViewModel
import com.google.android.material.search.SearchBar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val searchRepoViewModel by viewModels<SearchRepoViewModel>()
    private val searchUserViewModel by viewModels<SearchUserViewModel>()
    private var booleanUser = false
    private var booleanRepo = true

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val fadeOut = ObjectAnimator.ofFloat(
                splashScreenView,
                View.ALPHA,
                1f,
                0f
            )
            fadeOut.interpolator = DecelerateInterpolator()
            fadeOut.duration = 300L
            fadeOut.doOnEnd { splashScreenView.remove() }
            fadeOut.start()
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.appBar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        binding.rvRepo.layoutManager = layoutManager

        searchRepoViewModel.githubRepoSearch.observe(this){
            it -> setRepoData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val toolbar = findViewById<Toolbar>(R.id.appBar)
        when (item.itemId) {
            R.id.user -> {
                searchUserViewModel.githubUserData.observe(this){
                        it -> setUserData(it)
                }
                booleanUser = true
                booleanRepo = false
                if (this.booleanUser){
                    searchUser()
                }
                toolbar.menu.findItem(R.id.user).setIcon(user_fill_icon)
                toolbar.menu.findItem(R.id.repo).setIcon(repo_icon)
            }

            R.id.repo -> {
                searchRepoViewModel.githubRepoSearch.observe(this){
                        it -> setRepoData(it)
                }
                booleanUser = false
                booleanRepo = true
                if (this.booleanRepo){
                    searchRepo()
                }
                toolbar.menu.findItem(R.id.user).setIcon(user_icon)
                toolbar.menu.findItem(R.id.repo).setIcon(repo_fill_icon)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUserData(it: List<ItemsUser?>?){
        val adapter = SearchUserAdapter()
        adapter.submitList(it)
        binding.rvRepo.adapter = adapter

        adapter.setOnItemClickCallback(object : SearchUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsUser) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.USERNAME, data.login)
                startActivity(intent)
            }
        })
    }

    private fun setRepoData(it: List<ItemsItem?>?){
        val adapter = SearchRepoAdapter()
        adapter.submitList(it)
        binding.rvRepo.adapter = adapter

        adapter.setOnItemClickCallback(object : SearchRepoAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(data.htmlUrl)
                startActivity(intent)
            }
        })
    }

    private fun searchRepo(){
        if (booleanRepo){
            with(binding){
                searchTextInput.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        searchTextInputLayout.hint = null
                        val searchText = s?.toString()?.trim() ?: ""
                        searchRepoViewModel.getRepo(searchText)
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
                searchTextInput.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val searchText = searchTextInput.text.toString().trim()
                        searchRepoViewModel.getRepo(searchText)
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(searchTextInput.windowToken, 0)
                        true
                    } else {
                        false
                    }
                }
            }
        }else{
            with(binding){
                searchTextInput.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
            }
        }
    }

    private fun searchUser(){
        if (booleanUser){
            with(binding){
                searchTextInput.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        searchTextInputLayout.hint = null
                        val searchText = s?.toString()?.trim() ?: ""
                        searchUserViewModel.getUser(searchText)
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
                searchTextInput.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val searchText = searchTextInput.text.toString().trim()
                        searchUserViewModel.getUser(searchText)
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(searchTextInput.windowToken, 0)
                        true
                    } else {
                        false
                    }
                }
            }
        }else{
            with(binding){
                searchTextInput.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(s: Editable?) {}
                })
            }
        }
    }
}
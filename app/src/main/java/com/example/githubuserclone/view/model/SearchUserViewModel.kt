package com.example.githubuserclone.view.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserclone.data.response.GithubSearchUserRepoResponse
import com.example.githubuserclone.data.response.ItemsUser
import com.example.githubuserclone.data.retrofit.ApiConfig

class SearchUserViewModel: ViewModel() {
    private val _githubUserData = MutableLiveData<List<ItemsUser?>?>()
    val githubUserData: MutableLiveData<List<ItemsUser?>?> = _githubUserData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        val USERNAME = "fannan"
    }

    init {
        getUser(USERNAME)
    }

    fun getUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUserData(username)
        client.enqueue(object : retrofit2.Callback<GithubSearchUserRepoResponse?>{
            override fun onResponse(
                call: retrofit2.Call<GithubSearchUserRepoResponse?>,
                response: retrofit2.Response<GithubSearchUserRepoResponse?>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _githubUserData.value = response.body()?.items
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<GithubSearchUserRepoResponse?>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
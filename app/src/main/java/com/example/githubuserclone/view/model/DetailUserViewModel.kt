package com.example.githubuserclone.view.model

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserclone.data.response.GithubDetailUserRepoResponse
import com.example.githubuserclone.data.response.ItemsItem
import com.example.githubuserclone.data.response.ItemsUser
import com.example.githubuserclone.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
    private val _userDetail = MutableLiveData<GithubDetailUserRepoResponse>()
    val userDetail: LiveData<GithubDetailUserRepoResponse> = _userDetail

    private val _userDetailRepo = MutableLiveData<List<ItemsItem>>()
    val userDetailRepo: LiveData<List<ItemsItem>> = _userDetailRepo

    private val _userDetailFollower = MutableLiveData<List<ItemsUser>>()
    val userDetailFollower: LiveData<List<ItemsUser>> = _userDetailFollower

    private val _userDetailFollowing = MutableLiveData<List<ItemsUser>>()
    val userDetailFollowing: LiveData<List<ItemsUser>> = _userDetailFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : retrofit2.Callback<GithubDetailUserRepoResponse> {
            override fun onResponse(
                call: Call<GithubDetailUserRepoResponse>,
                response: Response<GithubDetailUserRepoResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                }else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubDetailUserRepoResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailUserRepos(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRepoUser(username)
        client.enqueue(object : retrofit2.Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetailRepo.value = response.body()
                }else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsUser>> {
            override fun onResponse(
                call: Call<List<ItemsUser>>,
                response: Response<List<ItemsUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetailFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsUser>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsUser>> {
            override fun onResponse(
                call: Call<List<ItemsUser>>,
                response: Response<List<ItemsUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetailFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsUser>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
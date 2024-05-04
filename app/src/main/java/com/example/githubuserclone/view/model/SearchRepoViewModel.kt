package com.example.githubuserclone.view.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserclone.data.response.GithubSearchRepoResponse
import com.example.githubuserclone.data.response.ItemsItem
import com.example.githubuserclone.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class SearchRepoViewModel: ViewModel() {
    private val _githubRepoSearch = MutableLiveData<List<ItemsItem?>?>()
    val githubRepoSearch: LiveData<List<ItemsItem?>?> = _githubRepoSearch

    private val _loadingData = MutableLiveData<Boolean>()
    val loadingData : LiveData<Boolean> = _loadingData

    companion object{
        private val TAG = "SearchRepoViewModel"
        private val query = "Python"
    }

    init {
        getRepo(query)
    }

    fun getRepo(query: String){
        _loadingData.value = true
        val client = ApiConfig.getApiService().searchRepoUser(query)
        client.enqueue(object : retrofit2.Callback<GithubSearchRepoResponse> {
            override fun onResponse(
                call: Call<GithubSearchRepoResponse>,
                response: Response<GithubSearchRepoResponse>
            ) {
                _loadingData.value = false
                if (response.isSuccessful) {
                    _githubRepoSearch.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubSearchRepoResponse>, t: Throwable) {
                _loadingData.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
package com.example.githubuserclone.data.retrofit

import com.example.githubuserclone.BuildConfig
import com.example.githubuserclone.data.response.GithubDetailUserRepoResponse
import com.example.githubuserclone.data.response.GithubSearchRepoResponse
import com.example.githubuserclone.data.response.GithubSearchUserRepoResponse
import com.example.githubuserclone.data.response.GithubUserRepoResponse
import com.example.githubuserclone.data.response.ItemsItem
import com.example.githubuserclone.data.response.ItemsUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/repositories")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun searchRepoUser(
        @Query("q") query: String
    ): Call<GithubSearchRepoResponse>

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun searchUserData(
        @Query("q") query: String
    ): Call<GithubSearchUserRepoResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getDetailUser(@Path("username") username: String): Call<GithubDetailUserRepoResponse>

    @GET("users/{username}/repos")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getRepoUser(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsUser>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsUser>>

}
package com.baharudindayat.githubuser.apiservice

import retrofit2.Call
import retrofit2.http.*

interface ApiService{
    @GET("search/users")
    fun getUsername(
        @Query("q") page: String

    ): Call<GithubResponse>

    @GET("users/{USERNAME}")
    fun getDetailProfile(
        @Path("USERNAME") id:String

    ): Call<DetailUser>

    @GET("users/{USERNAME}/following")
    fun getFollowing(
        @Path("USERNAME") id:String

    ): Call<List<FollowingItem>>

    @GET("users/{USERNAME}/followers")
    fun getFollowers(
        @Path("USERNAME") id:String

    ): Call<List<FollowersItem>>
}
package com.baharudindayat.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baharudindayat.githubuser.apiservice.ApiConfig
import com.baharudindayat.githubuser.apiservice.FollowersItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val _github = MutableLiveData<List<FollowersItem>>()
    val followers: LiveData<List<FollowersItem>> = _github

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object{
        const val TAG = "FollowerViewModel"
    }



    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<FollowersItem>> {
            override fun onResponse(
                call: Call<List<FollowersItem>>,
                response: Response<List<FollowersItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _github.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
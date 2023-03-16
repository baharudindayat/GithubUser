package com.baharudindayat.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baharudindayat.githubuser.apiservice.ApiConfig
import com.baharudindayat.githubuser.apiservice.FollowingItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    private val _github = MutableLiveData<List<FollowingItem>>()
    val following: LiveData<List<FollowingItem>> = _github

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object{
        const val TAG = "FollowingViewModel"
    }



    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<FollowingItem>> {
            override fun onResponse(
                call: Call<List<FollowingItem>>,
                response: Response<List<FollowingItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _github.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowingItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
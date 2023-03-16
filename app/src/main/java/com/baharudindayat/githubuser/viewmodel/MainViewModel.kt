package com.baharudindayat.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baharudindayat.githubuser.apiservice.ApiConfig
import com.baharudindayat.githubuser.apiservice.GithubResponse
import com.baharudindayat.githubuser.apiservice.ItemsItem
import com.baharudindayat.githubuser.event.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _github = MutableLiveData<List<ItemsItem>>()
    val items: LiveData<List<ItemsItem>> = _github

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object{
        const val TAG = "MainViewModel"
    }



    fun findUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsername(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _github.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
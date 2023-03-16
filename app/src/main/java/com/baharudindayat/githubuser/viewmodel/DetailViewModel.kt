package com.baharudindayat.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baharudindayat.githubuser.apiservice.ApiConfig
import com.baharudindayat.githubuser.apiservice.DetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _github = MutableLiveData<DetailUser>()
    val items: LiveData<DetailUser> = _github

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        const val TAG = "DetailViewModel"
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailProfile(username)
        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(
                call: Call<DetailUser>,
                response: Response<DetailUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _github.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
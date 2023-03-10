package com.arlanallacsta.githubuserapp2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {

    val detailUser =  MutableLiveData<DetailUserResponse>()

    fun setUserDetail(username: String){
        RetrofitObject.apiInstance.getUsersDetail(username).enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful){
                    detailUser.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("Failure", t.message!!)
            }

        })
    }

    fun getUserDetail() : LiveData<DetailUserResponse>{
        return detailUser
    }
}
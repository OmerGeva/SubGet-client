package com.example.subget

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("listings")
    fun getData(): Call<List<MyDataItem>>
}
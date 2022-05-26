package com.example.subget.app_data.remote_db

import com.example.subget.app_data.models.AllListings
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ListingService {

    @GET("listings")
    suspend fun getAllListings() : Response<AllListings>

}
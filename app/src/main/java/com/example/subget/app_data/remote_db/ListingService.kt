package com.example.subget.app_data.remote_db

import com.example.subget.app_data.models.AllListings
import com.example.subget.app_data.models.Listing
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ListingService {

    @GET("listings")
    suspend fun getAllListings() : Response<AllListings>

    @GET("listing/{id}")
    suspend fun getListing(@Path("id") id : Int) : Response<Listing>

}
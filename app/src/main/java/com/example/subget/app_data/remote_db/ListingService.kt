package com.example.subget.app_data.remote_db

import com.example.subget.app_data.models.AllListings
import com.example.subget.app_data.models.Listing
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ListingService {

    // Request all listings from API
    @GET("listings")
    suspend fun apiGetAllListings() : Response<AllListings>

    // post new listing to API
    @POST("listings")
    suspend fun apiCreateNewListing(@Body listing: Listing) : Response<Listing>
}
package com.example.subget.app_data.remote_db

import com.example.subget.app_data.models.AllListings
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.models.Stats
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ListingService {

    // Request all listings from API
    @GET("listings")
    suspend fun apiGetAllListings() : Response<AllListings>

    // Request all listings from API
    @GET("stats")
    suspend fun apiGetStats() : Response<Stats>

    // post new listing to API
    @POST("listings")
    suspend fun apiCreateNewListing(@Body listing: Listing) : Response<Listing>
}
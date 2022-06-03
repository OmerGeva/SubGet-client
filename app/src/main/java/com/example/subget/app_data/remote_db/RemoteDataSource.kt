package com.example.subget.app_data.remote_db

import com.example.subget.app_data.models.Listing
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val listingService: ListingService) : BaseDataSource() {

    // Get all listings from remote database
    suspend fun remoteGetAllListings() = getResult { listingService.apiGetAllListings() }

    // Upload a new listing to remote database
    suspend fun remoteCreateNewListing(listing: Listing) = getResult { listingService.apiCreateNewListing(listing) }
}
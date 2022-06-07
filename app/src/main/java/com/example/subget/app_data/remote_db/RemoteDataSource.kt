package com.example.subget.app_data.remote_db

import com.example.subget.app_data.models.Listing
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val listingService: ListingService) : BaseDataSource() {

    // Get all Listings from remote database
    suspend fun remoteGetAllListings() = getResult { listingService.apiGetAllListings() }

    // Get a single Listing from remote database
    suspend fun remoteGetSingleListing(id: Int) = getResult { listingService.apiGetSingleListing(id) }

    // Get all Stats from remote database
    suspend fun remoteGetStats() = getResult { listingService.apiGetStats() }

    // Upload a new Listing to remote database
    suspend fun remoteCreateNewListing(listing: Listing) = getResult { listingService.apiCreateNewListing(listing) }
}
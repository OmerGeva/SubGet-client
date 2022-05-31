package com.example.subget.app_data.remote_db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val listingService: ListingService) : BaseDataSource() {

    // Get all listings from remote database
    suspend fun remoteGetAllListings() = getResult { listingService.apiGetAllListings() }

}
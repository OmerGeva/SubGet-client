package com.example.subget.app_data.remote_db

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val listingService: ListingService) : BaseDataSource() {

    suspend fun remoteGetListings() = getResult { listingService.getAllListings() }
    suspend fun remoteGetSingleListing(id : Int) = getResult { listingService.getListing(id) }

}
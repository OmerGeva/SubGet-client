package com.example.subget.app_data.repository

import androidx.lifecycle.LiveData
import com.example.subget.app_data.local_db.DatabaseDAO
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.remote_db.RemoteDataSource
import com.example.subget.utils.Resource
import com.example.subget.utils.performFetchingAndSaving
import com.example.subget.utils.performFetchingAndSavingPost
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource : RemoteDataSource,
    private val localDataSource : DatabaseDAO
){

    // Fetch all Listings from local and remote databases
    fun repoFetchListings() = performFetchingAndSaving(
        {localDataSource.localGetAllListings()},
        {remoteDataSource.remoteGetAllListings()},
        {localDataSource.insert(it.listings)}
    )

    // Get all Listings that are classified as favorites from local database
    fun repoGetFavorites() : LiveData<List<Listing>> = localDataSource.localGetFavorites()

    // Get all Listings from local database
    fun repoGetAllListings() : LiveData<List<Listing>> = localDataSource.localGetAllListings()

    // Get a single Listing from local database
    fun repoGetSingleListing(id : Int) : LiveData<Listing> = localDataSource.localGetSingleListing(id)

    // Get all Listings that match search result from database
    fun repoGetSearchResults(location : String) : LiveData<List<Listing>> = localDataSource.localGetSearchResults(location)

    // Get all Favorites Listings that match search result from database
    fun repoGetFavoritesSearchResults(location : String) : LiveData<List<Listing>> = localDataSource.localGetFavoritesSearchResults(location)

    suspend fun repoCreateListing(listing: Listing) = performFetchingAndSavingPost(
        { remoteDataSource.remoteCreateNewListing(listing) },
        { localDataSource.insertOne(listing) }
    )

}
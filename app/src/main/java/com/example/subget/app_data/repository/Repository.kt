package com.example.subget.app_data.repository

import androidx.lifecycle.LiveData
import com.example.subget.app_data.local_db.DatabaseDAO
import com.example.subget.app_data.models.Favorite
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.remote_db.RemoteDataSource
import com.example.subget.utils.performGetAndSaving
import com.example.subget.utils.performPostAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource : RemoteDataSource,
    private val localDataSource : DatabaseDAO
){

    // Repository operations on Listings

    // Get all Listings from local and remote databases
    fun repoGetListings() = performGetAndSaving(
        {localDataSource.localGetAllListings()},
        {remoteDataSource.remoteGetAllListings()},
        {localDataSource.localInsertAllListings(it.listings)}
    )

    // Get a Listing from local and remote databases
    fun repoGetSingleListing(id: Int) = performGetAndSaving(
        {localDataSource.localGetSingleListing(id)},
        {remoteDataSource.remoteGetSingleListing(id)},
        {localDataSource.localInsertSingleListing(it)}
    )

    // Get all Listings that match search result from Listing table
    fun repoGetSearchResults(location : String) : LiveData<List<Listing>> =
        localDataSource.localGetSearchResults(location)

    // Post a Listing to local and remote databases
    fun repoPostListing(listing: Listing) = performPostAndSaving(
        { remoteDataSource.remoteCreateNewListing(listing) },
        { localDataSource.localInsertSingleListing(listing) }
    )



    // Repository operations on Favorites

    // Inserts a single Favorite into Favorite table
    fun repoInsertSingleFavorite(favorite: Favorite) =
        localDataSource.localInsertSingleFavorite(favorite)

    // Delete a single Favorite from Favorite table
    fun repoDeleteFavorite(listingID: Int) =
        localDataSource.localDeleteFavorite(listingID)

    // Check whether a Listing exists as a Favorite in Favorite table
    fun repoIsFavorite(listingID: Int) =
        localDataSource.localIsFavorites(listingID)



    // Repository operations on Stats

    // Get all Stats from local and remote databases
    fun repoGetStats() = performGetAndSaving(
        {localDataSource.localGetStats()},
        {remoteDataSource.remoteGetStats()},
        {localDataSource.localInsertStats(it)}
    )


    // Repository operation on more than one table

    // Get all Listings which are classified as favorites from Listing table
    fun repoGetFavorites() : LiveData<List<Listing>> = localDataSource.localGetFavorites()

    // Get all Listings which are classified as favorites and match search results from Listing table
    fun repoGetFavoritesSearchResults(location : String) : LiveData<List<Listing>> =
        localDataSource.localGetFavoritesSearchResults(location)
























}
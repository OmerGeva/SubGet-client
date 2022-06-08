package com.example.subget.app_data.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.subget.app_data.models.Favorite
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.models.Stats


@Dao
interface DatabaseDAO {

    // Operations for Listings table

    // Inserts a list of Listings into Listing table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun localInsertAllListings(listings: List<Listing>)

    // Inserts a single Listing into Listing table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun localInsertSingleListing(listing: Listing)

    // Get all Listings from Listing table
    @Query("SELECT * FROM listings")
    fun localGetAllListings(): LiveData<List<Listing>>

    // Get a single Listing from Listing table
    @Query("SELECT * from listings WHERE id = :id")
    fun localGetSingleListing(id: Int): LiveData<Listing>

    // Get all Listings which match the search result from Listing table
    @Query("SELECT * FROM listings WHERE address LIKE  :location")
    fun localGetSearchResults(location : String) : LiveData<List<Listing>>



    // Operations for Favorites table

    // Inserts a single Favorite into Favorite table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun localInsertSingleFavorite(favorite: Favorite)

    // Delete a single Favorite from Favorite table
    @Query("DELETE FROM favorites WHERE listingID = :listingID")
    fun localDeleteFavorite(listingID: Int)

    // Check whether a Listing exists as a Favorite in Favorite table
    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE listingID = :listingID)")
    fun localIsFavorites(listingID: Int): LiveData<Boolean>



    // Operations for Stats table

    // Inserts a single Stats into Stats table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun localInsertStats(stats: Stats)

    // Get all Stats from Stats table
    @Query("SELECT * FROM stats LIMIT 1")
    fun localGetStats() : LiveData<Stats>



    // INNER JOIN Operations

    // Get all Listings which are classified as favorites from Listing table
    @Query("SELECT * FROM listings INNER JOIN favorites ON favorites.listingID = listings.id")
    fun localGetFavorites(): LiveData<List<Listing>>

    // Get all Listings which are classified as favorites and match search results from Listing table
    @Query("SELECT * FROM listings INNER JOIN favorites ON favorites.listingID = listings.id WHERE address LIKE  :location")
    fun localGetFavoritesSearchResults(location : String) : LiveData<List<Listing>>
}
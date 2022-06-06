package com.example.subget.app_data.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.subget.app_data.models.Listing


@Dao
interface DatabaseDAO {

    // Inserts a list of Listings into Room
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun localInsertAllListings(listings: List<Listing>)

    // Inserts a single Listing into Room
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun localInsertListing(listing: Listing)

    // Get all Listings from Room
    @Query("SELECT * FROM listings")
    fun localGetAllListings(): LiveData<List<Listing>>

    // Get a single Listing from Room
    @Query("SELECT * from listings WHERE id = :id")
    fun localGetSingleListing(id: Int): LiveData<Listing>

    // Get all Listings which are classified as favorites from Room
    @Query("SELECT * FROM listings WHERE favorite = 1")
    fun localGetFavorites(): LiveData<List<Listing>>

    // Get all Listings which match the search result
    @Query("SELECT * FROM listings WHERE address LIKE  :location")
    fun localGetSearchResults(location : String) : LiveData<List<Listing>>

    // Get all favorite Listings which match the search result
    @Query("SELECT * FROM listings WHERE address LIKE  :location AND favorite = 1")
    fun localGetFavoritesSearchResults(location : String) : LiveData<List<Listing>>

    // Updates Listing favorite status
    @Query("UPDATE listings SET favorite = :favorite WHERE id = :listingId")
    fun localUpdateFavorite(listingId: Int, favorite: Boolean)

}
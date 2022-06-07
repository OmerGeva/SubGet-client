package com.example.subget.app_data.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.models.Stats


@Dao
interface DatabaseDAO {

    // Inserts a list of Listings into Listing table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun localInsertAllListings(listings: List<Listing>)

    // Inserts a single Listing into Listing table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun localInsertSingleListing(listing: Listing)

    // Inserts a single Stats into Stats table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun localInsertStats(stats: Stats)

    // Get all Stats from Stats table
    @Query("SELECT * FROM stats LIMIT 1")
    fun localGetStats() : LiveData<Stats>

    // Get all Listings from Listing table
    @Query("SELECT * FROM listings")
    fun localGetAllListings(): LiveData<List<Listing>>

    // Get a single Listing from Listing table
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

    // Updates Listing image
    @Query("UPDATE listings SET image = :image WHERE id = :listingId")
    fun localUpdateImage(listingId: Int, image: String)

    // Updates Listing favorite status
    @Query("UPDATE listings SET favorite = :favorite WHERE id = :listingId")
    fun localUpdateFavorite(listingId: Int, favorite: Boolean)

}
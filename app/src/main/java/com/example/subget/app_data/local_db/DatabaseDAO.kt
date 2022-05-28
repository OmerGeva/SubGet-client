package com.example.subget.app_data.local_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.subget.app_data.models.Listing


@Dao
interface DatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listings: List<Listing>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleListing(listing: Listing)

    // Finds a Listing by its id
    @Query("SELECT * from listings WHERE id = :id")
    fun getListing(id: Int): LiveData<Listing>

    // Get all Listings
    @Query("SELECT * FROM listings ORDER BY id DESC")
    fun localGetListings(): LiveData<List<Listing>>

}
package com.example.subget.listings_data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ListingDatabaseDAO {

    @Insert
    fun insert(listing: Listing)

    @Update
    fun update(listing: Listing)

    // Finds a Listing by its title
    @Query("SELECT * from listings_table WHERE listing_title = :title")
    fun get(title: String): Listing?

    // Get all Listings
    @Query("SELECT * FROM listings_table ORDER BY listingId DESC")
    fun getAllListings(): LiveData<List<Listing>>

}
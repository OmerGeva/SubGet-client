package com.example.subget.listings_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listings_table")
data class Listing(

    @PrimaryKey(autoGenerate = true)
    val listingID : Long,

    @ColumnInfo(name = "listing_title")
    val title : String,

    @ColumnInfo(name = "listing_description")
    val description : String,

    // Couldn't find the appropriate syntax
//    @ColumnInfo(name = "listing_pictures")
//    val pictures : MutableList<Int> = arrayListOf(),

    @ColumnInfo(name = "listing_address")
    val address : String,

    @ColumnInfo(name = "listing_price")
    val price : Double,

    @ColumnInfo(name = "listing_phone")
    val phoneNumber : String

    )

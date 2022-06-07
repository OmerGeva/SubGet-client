package com.example.subget.app_data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Stats(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val most_expensive_address: String,
    val least_expensive_address: String,
    val listing_count: Int,
    val listings_price_min: Int,
    val listings_price_max: Int,
    val listings_price_avg: Int,
    val increase_from_last_qtr: String
)

package com.example.subget.app_data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val listingID: Int
)

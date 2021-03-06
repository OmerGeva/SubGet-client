package com.example.subget.app_data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listings")
data class Listing(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val address: String,
    val phone_number: String,
    val contact_name: String,
    val washing_machine: Boolean,
    val pet_allowed: Boolean,
    val near_beach: Boolean,
    val wifi: Boolean,
    val bedrooms: Int,
    val bathrooms: Int,
    val floor: Int,
    val price: Int,
    var image: String
)





package com.example.subget.listings_data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Listing::class], version = 1, exportSchema = false)
abstract class ListingDatabase : RoomDatabase() {

    abstract val listingDatabaseDao: ListingDatabaseDAO

    // Read about the companion object
    // https://developer.android.com/codelabs/kotlin-android-training-room-database?index=..%2F..android-kotlin-fundamentals#5
    companion object {

        @Volatile
        private var INSTANCE: ListingDatabase? = null

        fun getInstance(context: Context): ListingDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ListingDatabase::class.java,
                        "listings_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }




}
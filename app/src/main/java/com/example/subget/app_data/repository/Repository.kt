package com.example.subget.app_data.repository

import com.example.subget.app_data.local_db.DatabaseDAO
import com.example.subget.app_data.remote_db.RemoteDataSource
import com.example.subget.utils.performFetchingAndSaving
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val remoteDataSource : RemoteDataSource,
    private val localDataSource : DatabaseDAO
){

    fun repoGetListings() = performFetchingAndSaving(
        {localDataSource.localGetListings()},
        {remoteDataSource.remoteGetListings()},
        {localDataSource.insert(it.listings)}
    )

    fun getSingleListing(id : Int) = performFetchingAndSaving(
        {localDataSource.getListing(id)},
        {remoteDataSource.remoteGetSingleListing(id)},
        {localDataSource.insertSingleListing(it)}
    )

}
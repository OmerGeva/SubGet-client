package com.example.subget.utils

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.subget.app_data.models.Listing
import kotlinx.coroutines.Dispatchers


fun <T,A> performGetAndSaving(localDbFetch: () -> LiveData<T>,
                              remoteDbFetch: suspend () -> Resource<A>,
                              localDbSave: suspend (A) -> Unit) : LiveData<Resource<T>> =

    liveData(Dispatchers.IO) {

        emit(Resource.loading())

        val source = localDbFetch().map { Resource.success(it) }
        emitSource(source)

        val fetchResource = remoteDbFetch()

        if(fetchResource.status is Success)
            localDbSave(fetchResource.status.data!!)

        else if(fetchResource.status is Error){
            emit(Resource.error(fetchResource.status.message))
            emitSource(source)
        }
    }
fun <T> performPostAndSaving(
    remoteDbPost: suspend () -> Resource<T>,
    localDbSave: suspend (T) -> Unit) : LiveData<Resource<T>> =


    liveData(Dispatchers.IO) {

        emit(Resource.loading())

        val postResource = remoteDbPost()

        if(postResource.status is Success){
            localDbSave(postResource.status.data!!)
            emit(postResource)
        }

        else if(postResource.status is Error){
            emit(Resource.error(postResource.status.message))
        }
    }
package com.example.subget.ui.listing_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
private val repository: Repository
): ViewModel() {

    private val _id =  MutableLiveData<Int>()

    private val _listing = _id.switchMap {
        repository.repoGetSingleListing(it)
    }

    val listing : LiveData<Listing> = _listing

    fun setId(id : Int) {
        _id.value = id
    }
}
package com.example.subget.ui.add_listing

import androidx.lifecycle.*
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.repository.Repository
import com.example.subget.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddListingViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // Create the Listing
    private val _listing =  MutableLiveData<Listing>()
    fun setListing(listing: Listing) {
        _listing.value = listing
    }

    // Upload Listing and get
    private val _newListing = _listing.switchMap {
        repository.repoPostListing(it)
    }
    val newListing : LiveData<Resource<Listing>> = _newListing

}
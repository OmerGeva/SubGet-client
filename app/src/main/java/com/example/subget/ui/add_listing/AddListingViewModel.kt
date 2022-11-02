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

    // New Listing
    var newListing : LiveData<Resource<Listing>>? = null

    // Send POST request with the new Listing
    fun viewModelPostListing(listing: Listing) {
        newListing = repository.repoPostListing(listing)
    }

}
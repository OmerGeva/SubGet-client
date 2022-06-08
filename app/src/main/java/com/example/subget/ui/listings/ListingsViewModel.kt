package com.example.subget.ui.listings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListingsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    // Get all Listings from local and remote databases
    val listings = repository.repoGetListings()

    // Get all Listings from local database
    val offlineListings = repository.repoOfflineGetListings()

    // Populate RecyclerView with search results
    fun viewModelGetSearchResults(location: String) : LiveData<List<Listing>> =
        repository.repoGetSearchResults(location)
}

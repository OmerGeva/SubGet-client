package com.example.subget.ui.listings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListingsViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    // All listings
    val listings  = repository.repoFetchListings()

    // Populate RecyclerView with search results
    private val repo = repository
    fun searchForListings(location: String) : LiveData<List<Listing>> {
       return repo.repoGetSearchResults(location)
    }

}

package com.example.subget.ui.add_listing

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.subget.R
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.repository.Repository
import com.example.subget.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddListingViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var newListing: LiveData<Resource<Listing>>? = null

    // Saves a Listing in remote and local database
    fun viewModelCreateListing(listing : Listing) = viewModelScope.launch { async(IO) {
        newListing = repository.repoCreateListing(listing) }
    }
}
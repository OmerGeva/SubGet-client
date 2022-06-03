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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddListingViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    var repo = repository
    var listingDetail: LiveData<Resource<Listing>>? = null

    fun addItem(listing : Listing) {
        viewModelScope.launch {
            listingDetail = repo.repoCreateListing(listing)
        }
    }


}
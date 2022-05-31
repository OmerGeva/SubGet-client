package com.example.subget.ui.add_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddListingViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    var repo = repository

    fun addItem(listing : Listing) {
        viewModelScope.launch {
            // POST REQUEST
        }
    }


}
package com.example.subget.ui.listing_detail

import androidx.lifecycle.*
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
private val repository: Repository
): ViewModel() {

    // Get the ID of a specific Listing
    private val _id =  MutableLiveData<Int>()
    fun setId(id : Int) {
        _id.value = id
    }

    // Get a specific Listing from local database
    val listing : LiveData<Listing> = _id.switchMap {
        repository.repoGetSingleListing(it)
    }

    // Update the favorite status of a Listing
    fun viewModelUpdateFavorite() = viewModelScope.launch { async(IO) {
        repository.repoUpdateFavorite(listing.value!!.id, !listing.value!!.favorite) }
    }



}
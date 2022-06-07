package com.example.subget.ui.listing_detail

import androidx.lifecycle.*
import com.example.subget.app_data.models.Favorite
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.repository.Repository
import com.example.subget.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
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

    // Check if specific Listing is present in Favorite table
    private val _favorite = _id.switchMap {
        repository.repoIsFavorite(it)
    }
    val favorite : LiveData<Boolean> = _favorite

    // Get a specific Listing from Listing table
    private val _listing = _id.switchMap {
        repository.repoGetSingleListing(it)
    }
    val listing : LiveData<Resource<Listing>> = _listing


    // Updates (ADD / REMOVE) Listings favorite status in Favorite table
    fun viewModelDeleteFavorite() = viewModelScope.launch { async(IO) {
        repository.repoDeleteFavorite(listing.value!!.status.data!!.id) } }
    fun viewModelAddFavorite() = viewModelScope.launch { async(IO) {
        repository.repoInsertSingleFavorite(Favorite(listing.value!!.status.data!!.id)) } }



}
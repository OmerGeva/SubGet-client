package com.example.subget.ui.listing_detail

import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.ContentProviderCompat.requireContext
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
        Log.d("SECOND STEP", _id.value.toString())
    }

    // Check if specific Listing is present in Favorite table
    private val _favorite = _id.switchMap {
        repository.repoIsFavorite(it)

    }
    val favorite : LiveData<Boolean> = _favorite


    // Get a specific Listing from local and remote databases
    private val _listing = _id.switchMap {
        repository.repoGetSingleListing(it)
    }
    val listing : LiveData<Resource<Listing>> = _listing

    // Get a specific Listing from local database
    private val _offlineListing = _id.switchMap {
        repository.repoOfflineGetSingleListing(it)
    }
    val offlineListing = _offlineListing

    // Updates (ADD / REMOVE) Listings favorite status in Favorite table
    fun viewModelDeleteFavorite() = viewModelScope.launch { async(IO) {
        repository.repoDeleteFavorite(_id.value!!) } }

    fun viewModelAddFavorite() = viewModelScope.launch { async(IO) {
        repository.repoInsertSingleFavorite(Favorite(_id.value!!)) } }
}
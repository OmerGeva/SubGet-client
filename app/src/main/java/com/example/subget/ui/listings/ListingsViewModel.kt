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

    private val _address =  MutableLiveData<String>()

    private val _listing = _address.switchMap {
        repository.repoGetListingByAddress(it)
    }
//    val listings  = repository.repoGetAllListings()
    val listings : LiveData<List<Listing>> = _listing

    fun setAddress(address : String) {
        _address.value = address
    }
}




//{
//
//    val listings  = repository.repoGetAllListings()
//}
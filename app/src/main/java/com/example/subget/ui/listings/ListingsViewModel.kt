package com.example.subget.ui.listings

import androidx.lifecycle.ViewModel
import com.example.subget.app_data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListingsViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    val listings  = repository.repoGetListings()
}
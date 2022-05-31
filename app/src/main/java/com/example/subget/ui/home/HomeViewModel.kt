package com.example.subget.ui.home

import androidx.lifecycle.ViewModel
import com.example.subget.app_data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    val listings  = repository.repoFetchListings()
}
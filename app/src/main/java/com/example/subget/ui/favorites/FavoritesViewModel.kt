package com.example.subget.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.subget.app_data.models.Listing
import com.example.subget.app_data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    // Get all Favorite Listings
    val favorites  = repository.repoGetFavorites()

    // Populate RecyclerView with search results
    private val repo = repository
    fun viewModelGetFavoritesSearchResults(location: String) : LiveData<List<Listing>> =
        repo.repoGetFavoritesSearchResults(location)
}
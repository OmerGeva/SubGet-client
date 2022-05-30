package com.example.subget.ui.favorites

import androidx.lifecycle.ViewModel
import com.example.subget.app_data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    val favorites  = repository.repoGetFavorites()
}
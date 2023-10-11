package com.example.kotlinize.favorites.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinize.model.RepositoryInterface

class FavoritesViewModelFactory(private val _repo: RepositoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            FavoritesViewModel(_repo) as T
        } else {
            throw java.lang.IllegalArgumentException("No Such ViewModel")
        }
    }
}
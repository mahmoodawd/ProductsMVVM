package com.example.kotlinize.allproducts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinize.model.RepositoryInterface

class ProductsViewModelFactory(private val _repo: RepositoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            ProductsViewModel(_repo) as T
        } else {
            throw java.lang.IllegalArgumentException("No Such ViewModel")
        }
    }
}
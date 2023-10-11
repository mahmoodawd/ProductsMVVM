package com.example.kotlinize.allproducts.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinize.model.Product
import com.example.kotlinize.model.RepositoryInterface
import com.example.kotlinize.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductsViewModel(private val repository: RepositoryInterface) : ViewModel() {
    private val _postStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Loading)
    val postStateFlow: StateFlow<ApiState> = _postStateFlow

    private val TAG = "ProductsViewModel"
    val errorMessage = MutableLiveData<String>()
    var productList = MutableLiveData<List<Product>>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()


    fun getProducts() {

        viewModelScope.launch {

            repository.getProductListFromAPI().catch { e ->
                _postStateFlow.value = ApiState.Failure(e)
                Log.i(TAG, "getProductListFromAPI: Failed ${e.message}")
            }.collect { data ->
                _postStateFlow.value = ApiState.Success(data)
                Log.i(TAG, "getProductListFromAPI: Success")
            }
        }

    }

    fun saveToFav(product: Product) {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val result = repository.addToFav(product)
            withContext(Dispatchers.Main) {
                withContext(Dispatchers.Main) {
                    if (result > 0) {
                        Log.i(TAG, "saveToFav: Saved")
                    }
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
}
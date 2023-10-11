package com.example.kotlinize.favorites.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.kotlinize.db.ProductDao
import com.example.kotlinize.db.ProductsDB
import com.example.kotlinize.model.Product
import com.example.kotlinize.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoritesViewModel(private val repository: RepositoryInterface) : ViewModel() {
    private val TAG = "FavoritesViewModel"


    fun getProductListFromLocal(): MutableLiveData<List<Product>> {
        var productList = MutableLiveData<List<Product>>()
        viewModelScope.launch {
            repository.getFavorites().flowOn(Dispatchers.IO).catch { e ->
                // handle exception
            }.collect {
                productList.postValue(it)
            }
        }
        return productList
    }

    fun removeFromFav(product: Product): MutableLiveData<Boolean> {
        val status = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val st = repository.removeFromFav(product)
            withContext(Dispatchers.Main) {
                if (st > 0) {

                    status.value = true
                    Log.i(TAG, "removeFromFav: ItemRemoved")
                } else {
                    status.value = false
                    Log.i(TAG, "removeFromFav: failed")

                }
            }
        }
        return status
    }
}
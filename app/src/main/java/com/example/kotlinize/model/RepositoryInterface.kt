package com.example.kotlinize.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getProductListFromAPI(): Flow<List<Product>>
     fun getFavorites(): Flow<List<Product>>
    suspend fun addToFav(product: Product): Long
    suspend fun removeFromFav(product: Product): Int


}
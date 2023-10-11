package com.example.kotlinize.db

import com.example.kotlinize.model.Product
import kotlinx.coroutines.flow.Flow

interface LocalSource {

     fun getFavorites(): Flow<List<Product>>
    suspend fun addProduct(product: Product):Long
    suspend fun deleteProduct(product: Product):Int

}
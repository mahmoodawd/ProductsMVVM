package com.example.kotlinize.db

import android.content.Context
import com.example.kotlinize.model.Product

class ProductLocalDataSource(val context: Context) : LocalSource {

    private val productDao: ProductDao by lazy {
        ProductsDB.getInstance(context).getProductDao()
    }

    override  fun getFavorites() = productDao.getAll()

    override suspend fun addProduct(product: Product): Long = productDao.insert(product)

    override suspend fun deleteProduct(product: Product): Int = productDao.delete(product)
}
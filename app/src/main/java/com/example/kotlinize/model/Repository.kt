package com.example.kotlinize.model

import com.example.kotlinize.db.LocalSource
import com.example.kotlinize.network.RemoteSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository private constructor(
    private val remoteSource: RemoteSource,
    private val concreteLocalSource: LocalSource
) : RepositoryInterface {

    private val TAG = "Repo"

    companion object {
        private var instance: Repository? = null

        fun getInstance(
            remoteSource: RemoteSource,
            concreteLocalSource: LocalSource
        ): Repository {
            if (instance == null) {
                instance = Repository(remoteSource, concreteLocalSource)
            }
            return instance as Repository
        }
    }


    override suspend fun getProductListFromAPI(): Flow<List<Product>> {
        return flow { emit(remoteSource.getProducts().body()?.products as List<Product>) }.flowOn(
            Dispatchers.IO
        )
    }

    override fun getFavorites() = concreteLocalSource.getFavorites()

    override suspend fun addToFav(product: Product): Long = concreteLocalSource.addProduct(product)
    override suspend fun removeFromFav(product: Product): Int =
        concreteLocalSource.deleteProduct(product)

}


package com.example.kotlinize.network

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteSource{
    suspend fun getProducts(): Response<ProductsResponse>
}
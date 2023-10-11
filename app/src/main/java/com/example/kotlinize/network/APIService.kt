package com.example.kotlinize.network

import com.example.kotlinize.model.Product
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("products")
     suspend fun getProducts(): Response<ProductsResponse>
}

data class ProductsResponse(
    val total: Int? = null,
    val limit: Int? = null,
    val skip: Int? = null,
    val products: List<Product?>? = null
) {

}

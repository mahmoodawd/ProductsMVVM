package com.example.kotlinize.network

import com.example.kotlinize.model.Product

sealed class ApiState {
    class Success(val products: List<Product>) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()
}

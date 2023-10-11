package com.example.kotlinize

import com.example.kotlinize.model.Product

interface Communicator {

    fun passProduct(product: Product): Unit
}
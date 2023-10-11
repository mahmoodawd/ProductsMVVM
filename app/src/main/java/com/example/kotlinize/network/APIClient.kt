package com.example.kotlinize.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitHelper {
    private const val BASE_URL = "https://dummyjson.com/"
    private fun createMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofitInstance =
        Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(createMoshi())
            ).baseUrl(BASE_URL)
            .build()
}

object APIClient : RemoteSource {
    val retrofitService: APIService by lazy {
        RetrofitHelper.retrofitInstance.create(APIService::class.java)
    }

    override suspend fun getProducts() = retrofitService.getProducts()


}
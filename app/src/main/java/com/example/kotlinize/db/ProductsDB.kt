package com.example.kotlinize.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinize.model.Product

@Database([Product::class], version = 1)
abstract class ProductsDB : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    companion object {
        private var INSTANCE: ProductsDB? = null

        fun getInstance(context: Context): ProductsDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductsDB::class.java,
                    "product-db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
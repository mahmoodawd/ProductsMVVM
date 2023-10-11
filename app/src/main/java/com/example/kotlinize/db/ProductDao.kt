package com.example.kotlinize.db

import androidx.room.*
import com.example.kotlinize.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
     fun getAll(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product):Long

    @Delete
    suspend fun delete(product: Product):Int
}
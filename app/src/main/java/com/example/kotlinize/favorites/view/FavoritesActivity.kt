package com.example.kotlinize.favorites.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinize.allproducts.view.adapters.ProductsAdapter
import com.example.kotlinize.databinding.ActivityFavoritesBinding
import com.example.kotlinize.db.ProductLocalDataSource
import com.example.kotlinize.favorites.viewModel.FavoritesViewModel
import com.example.kotlinize.favorites.viewModel.FavoritesViewModelFactory
import com.example.kotlinize.model.Product
import com.example.kotlinize.model.Repository
import com.example.kotlinize.network.APIClient

class FavoritesActivity : AppCompatActivity() {
    lateinit var favViewModel: FavoritesViewModel
    lateinit var activityFavoritesBinding: ActivityFavoritesBinding
    lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFavoritesBinding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(activityFavoritesBinding.root)
        initUI()
        setUpViewModel()
        getFavorites()
    }

    private fun initUI() {
        productsAdapter = ProductsAdapter(this, listOf()) {
            removeProduct(it)
        }
        activityFavoritesBinding.productsRecyclerView.apply {
            adapter = productsAdapter
            layoutManager =
                GridLayoutManager(this.context, 2)
                    .apply { orientation = RecyclerView.VERTICAL }
        }
    }

    private fun setUpViewModel() {
        val favVMFactory = FavoritesViewModelFactory(
            Repository.getInstance(
                APIClient,
                ProductLocalDataSource(this)
            )
        )
        favViewModel = ViewModelProvider(this, favVMFactory)[FavoritesViewModel::class.java]
    }


    private fun getFavorites() {
        favViewModel.getProductListFromLocal().observe(this) {
            productsAdapter.products = it
            productsAdapter.notifyDataSetChanged()
        }
    }

    private fun removeProduct(product: Product) {
        val position = productsAdapter.products.indexOf(product)
        productsAdapter.notifyItemRemoved(position)
        favViewModel.removeFromFav(product).observe(this) { status ->
            if (status == true) {
                Toast.makeText(this, "${product.title}, removed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
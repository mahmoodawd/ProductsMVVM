package com.example.kotlinize.allproducts.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinize.Communicator
import com.example.kotlinize.allproducts.view.adapters.ProductsAdapter
import com.example.kotlinize.allproducts.viewmodel.ProductsViewModel
import com.example.kotlinize.allproducts.viewmodel.ProductsViewModelFactory
import com.example.kotlinize.databinding.FragmentProductBinding
import com.example.kotlinize.db.ProductLocalDataSource
import com.example.kotlinize.model.Product
import com.example.kotlinize.model.Repository
import com.example.kotlinize.network.APIClient
import com.example.kotlinize.network.ApiState
import kotlinx.coroutines.launch


class ProductFragment : Fragment() {

    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var productList: List<Product>
    lateinit var binding: FragmentProductBinding
    lateinit var productsAdapter: ProductsAdapter
    lateinit var communicator: Communicator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productList = listOf()
        initUI(view)
        val productsViewModelFactory = ProductsViewModelFactory(
            Repository.getInstance(
                APIClient,
                ProductLocalDataSource(requireContext())
            )
        )
        productsViewModel =
            ViewModelProvider(this, productsViewModelFactory)[ProductsViewModel::class.java]
        collectProducts()
        productsViewModel.getProducts()
    }

    private fun collectProducts() {
        lifecycleScope.launch {
            productsViewModel.postStateFlow.collect { result ->
                when (result) {
                    is ApiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.productsRecyclerView.visibility = View.GONE
                    }
                    is ApiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.productsRecyclerView.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.progressBar.visibility = View.GONE
                        binding.productsRecyclerView.visibility = View.GONE
                        binding.errorIv.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Failed To Fetch Data", Toast.LENGTH_SHORT)
                            .show()

                    }
                }
            }
        }

    }


    private fun initUI(view: View) {
        productsAdapter = ProductsAdapter(view.context, productList) {
            productsViewModel.saveToFav(it)
            Toast.makeText(requireContext(), "Added 2 FAV", Toast.LENGTH_SHORT).show()
        }
        binding.productsRecyclerView.apply {
            adapter = productsAdapter
            layoutManager =
                GridLayoutManager(this.context, 2)
                    .apply { orientation = RecyclerView.VERTICAL }
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = context as Communicator
    }


}
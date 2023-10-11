package com.example.kotlinize.allproducts.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinize.model.Product
import com.example.kotlinize.databinding.ProductItemLayoutBinding
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private val context: Context,
    var products: List<Product>,
    private val onItemClick: (Product) -> Unit
) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    lateinit var productItemLayoutBinding: ProductItemLayoutBinding

    inner class ViewHolder(var binding: ProductItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        productItemLayoutBinding = ProductItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(productItemLayoutBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = products[position]
        Picasso.get().load(
            currentProduct.thumbnail
        ).into(holder.binding.productImageView)
        holder.binding.productTitleTextView.text = currentProduct.title

        holder.binding.iconFav.setOnClickListener {
            onItemClick(currentProduct)
        }
    }


    override fun getItemCount(): Int = products.size
}

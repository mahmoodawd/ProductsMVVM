package com.example.kotlinize.allproducts.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlinize.databinding.FragmentDetailsBinding
import com.example.kotlinize.model.Product
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {

    lateinit var detailsFragmentBinding:FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        detailsFragmentBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return detailsFragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = arguments?.getParcelable<Product>("product")
        buildViews(product)

    }


    private fun buildViews(product: Product?) {
        Picasso.get().load(
            product?.thumbnail
        ).into(detailsFragmentBinding.productImageView)

        detailsFragmentBinding.brandTitleTextView.text = "${product?.brand}-${product?.title}"
        detailsFragmentBinding.productDescriptionTextView.text = product?.description
        detailsFragmentBinding.priceTextView.text = "${product?.price}$"
        detailsFragmentBinding.productRating.rating = product?.rating ?: 0.0f
    }

}
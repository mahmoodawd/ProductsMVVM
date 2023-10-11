package com.example.kotlinize.allproducts.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinize.databinding.ActivitySecondBinding
import com.example.kotlinize.allproducts.view.fragments.DetailsFragment
import com.example.kotlinize.model.Product

class SecondActivity : AppCompatActivity() {
    lateinit var activitySecondBinding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(activitySecondBinding.root)


        val product = intent.getParcelableExtra<Product>("product")
        // Use the product object as needed

        // Pass the product object to the fragment
        val detailsFragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable("product", product)
        detailsFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(activitySecondBinding.fragmentContainerView.id, detailsFragment)
            .commit()
    }

    override fun onStart() {

        val currentOrientation = resources.configuration.orientation
        super.onStart()

        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE)
            finish()


    }
}
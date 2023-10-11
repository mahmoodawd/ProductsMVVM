package com.example.kotlinize.allproducts.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinize.Communicator
import com.example.kotlinize.databinding.ActivityFirstBinding
import com.example.kotlinize.allproducts.view.fragments.DetailsFragment
import com.example.kotlinize.model.Product

class FirstActivity : AppCompatActivity(), Communicator {

    lateinit var activityFirstBinding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFirstBinding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(activityFirstBinding.root)


    }

    override fun passProduct(product: Product) {
        val currentOrientation = resources.configuration.orientation

        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {

            val detailsFragment = DetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable("product", product)
            detailsFragment.arguments = bundle

            activityFirstBinding.fragmentContainerView2?.let {
                supportFragmentManager.beginTransaction()
                    .replace(it.id, detailsFragment)
                    .commit()
            }
        } else {

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }
    }
}
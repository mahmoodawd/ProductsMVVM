package com.example.kotlinize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinize.allproducts.view.FirstActivity
import com.example.kotlinize.databinding.ActivityHomeBinding
import com.example.kotlinize.favorites.view.FavoritesActivity

class HomeActivity : AppCompatActivity() {
    lateinit var activityHomeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        activityHomeBinding.buttonAllProducts.setOnClickListener {
            startActivity(Intent(this, FirstActivity::class.java))
        }
        activityHomeBinding.buttonFavorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

    }
}
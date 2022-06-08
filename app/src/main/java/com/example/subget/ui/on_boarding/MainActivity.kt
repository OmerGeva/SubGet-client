package com.example.subget.ui.on_boarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.example.subget.databinding.ActivityMainBinding
import androidx.navigation.ui.setupWithNavController
import com.example.subget.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.navigation_home -> binding.navView.visibility = View.VISIBLE
                R.id.navigation_details -> binding.navView.visibility = View.GONE
                R.id.navigation_listings -> binding.navView.visibility = View.VISIBLE
                R.id.navigation_favorites -> binding.navView.visibility = View.VISIBLE
                R.id.navigation_addListing -> binding.navView.visibility = View.VISIBLE
            }
        }
    }
}
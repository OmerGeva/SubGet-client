package com.example.subget.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
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



        // Bottom Navigation Bar
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_addListing, R.id.navigation_favorites
            )
        )

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.navigation_details -> hideBottomNav()
                R.id.navigation_listings -> showBottomNav()
//                R.id.navigation_favorites -> showBottomNav()
                R.id.navigation_home -> showBottomNav()
            }
        }

//        binding.detailBtn.setOnClickListener {
//            replaceFragment(DetailsFragment())
//        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }

    // Hide the NavBar for fragments who don't use it
    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }
    private fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }

}
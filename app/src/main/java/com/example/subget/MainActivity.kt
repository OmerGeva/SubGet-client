package com.example.subget

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.subget.databinding.ActivityMainBinding
import com.example.subget.favorites.FavoritesFragment
import com.example.subget.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.homeBtn.setOnClickListener {
            replaceFragment(HomeFragment())
        }
        binding.favoritesBtn.setOnClickListener {
            replaceFragment(FavoritesFragment())
        }
        binding.uploadBtn.setOnClickListener {
            //TODO: navigate the button to the desired location
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }

}
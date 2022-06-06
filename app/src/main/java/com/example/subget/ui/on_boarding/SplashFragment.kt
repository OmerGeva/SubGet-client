package com.example.subget.ui.on_boarding

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.subget.R


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        Handler().postDelayed({

            if (onBoardingFinished()) {
                findNavController().navigate(R.id.action_navigation_splash_to_navigation_home)
            } else {
                findNavController().navigate(R.id.action_navigation_splash_to_viewPagerFragment)
            }

        }, 3000)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}
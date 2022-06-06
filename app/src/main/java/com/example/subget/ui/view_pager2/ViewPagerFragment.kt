package com.example.subget.ui.view_pager2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.subget.R
import com.example.subget.databinding.FragmentListingsBinding
import com.example.subget.databinding.FragmentViewPagerBinding
import com.example.subget.ui.add_listing.AddListingFragment
import com.example.subget.ui.favorites.FavoritesFragment
import com.example.subget.ui.home.HomeFragment
import com.example.subget.ui.listings.ListingsFragment
import com.example.subget.ui.on_boarding.OnBoardingOne
import com.example.subget.ui.on_boarding.OnBoardingTwo


class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val fragmentList = arrayListOf<Fragment>(
            OnBoardingOne(),
            OnBoardingTwo()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter


        return binding.root
    }
}
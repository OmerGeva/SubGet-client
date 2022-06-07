package com.example.subget.ui.on_boarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.subget.R
import com.example.subget.databinding.FragmentListingsBinding
import com.example.subget.databinding.FragmentOnBoardingOneBinding


class OnBoardingOne : Fragment() {

    private var _binding: FragmentOnBoardingOneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingOneBinding.inflate(inflater, container, false)


        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        binding.next.setOnClickListener {
            viewPager?.currentItem = 1

        }


        return binding.root
    }
}
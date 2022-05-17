package com.example.subget.listings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.subget.R
import com.example.subget.databinding.FragmentDetailsBinding
import com.example.subget.databinding.FragmentListingsBinding


class ListingsFragment : Fragment() {

    private var _binding: FragmentListingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListingsBinding.inflate(inflater, container, false)

        return binding.root
    }
}
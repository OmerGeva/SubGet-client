package com.example.subget.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.subget.R
import com.example.subget.databinding.FragmentDetailsBinding
import com.example.subget.databinding.FragmentHomeBinding
import com.example.subget.ui.listings.ListingsViewModel
import com.example.subget.utils.Error
import com.example.subget.utils.Loading
import com.example.subget.utils.Success
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel : HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
    }

    private fun fetchData() {
        viewModel.listings.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.loadingText.visibility = View.VISIBLE
                }

                is Success -> {
                    binding.loadingBar.visibility = View.INVISIBLE
                    binding.loadingText.text = "SUCCESS MESSAGE"
                }

                is Error -> {
                    binding.loadingBar.visibility = View.INVISIBLE
                    binding.loadingText.text = "ERROR MESSAGE"
                }
            }
        }
    }
}
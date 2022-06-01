package com.example.subget.ui.listings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subget.R
import com.example.subget.databinding.FragmentListingsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListingsFragment : Fragment() {

    private val viewModel : ListingsViewModel by viewModels()
    private  lateinit var  adapter: ListingAdapter

    private var _binding: FragmentListingsBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("address")?.let { viewModel.setAddress(it) }
        viewModel.listings.observe(viewLifecycleOwner) { createRecyclerView() }
        createRecyclerView()
    }

    private fun createRecyclerView() {
        binding.listingRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListingAdapter(this@ListingsFragment)
        binding.listingRecycler.adapter = adapter
        viewModel.listings.observe(viewLifecycleOwner) { adapter.setListings(it) }
    }



    fun onItemClicked(listingID : Int) {
        findNavController().navigate(R.id.action_allListings_to_detailedListing,
            bundleOf("id" to listingID))
    }

    fun onItemLongClick(adapterPosition: Int) {
        print(false)
    }
}
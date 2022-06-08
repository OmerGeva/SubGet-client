package com.example.subget.ui.listings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subget.R
import com.example.subget.databinding.FragmentListingsBinding
import com.example.subget.utils.*

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

        // Shows Search window
        activity?.setActionBar(activity?.findViewById(R.id.toolbar))

        // Disable back button to increase intended usage of navigation bar
        disableBackButton()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        binding.listingRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListingAdapter(this@ListingsFragment)
        binding.listingRecycler.adapter = adapter

        // Checks whether user has internet connection
        val hasConnection = requireActivity().internetEnabled()

        // Populates RecyclerView according to Online/Offline mode
        if (hasConnection) { getOnlineListings() }
        else { getOfflineListings() }

        // Search window feature
        getSearchResults()


    }

    // Retrieves user input query
    private fun getSearchResults() {
        binding.toolbarSearch.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) { getSearchResults(query) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) { getSearchResults(newText) }
                return true
            }
        })
    }

    // Retrieves all Listings from local database that match the query
    private fun getSearchResults(searchText: String) {
        var searchText = "%$searchText%"
        viewModel.viewModelGetSearchResults(location = searchText).observe(viewLifecycleOwner) {
            adapter.setListings(it)}
    }

    // Populates RecyclerView with data from local and remote databases
    private fun getOnlineListings() {
        viewModel.listings.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> { binding.loadingScreen.visibility = View.VISIBLE }

                is Success -> {
                    if (it.status.data != null) {
                        binding.loadingScreen.visibility = View.GONE
                        binding.recyclerLayout.visibility = View.VISIBLE
                        adapter.setListings(it.status.data)
                    }
                }

                is Error -> {
                    requireActivity().dialog(getString(R.string.error_dialog)
                            + it.status.message + getString(R.string.error_dialog_cont))
                }
            }
        }
    }

    // Populates RecyclerView with data from local database
    private fun getOfflineListings() {
        viewModel.offlineListings.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding.recyclerLayout.visibility = View.VISIBLE
                adapter.setListings(it)
            } else {
                binding.recyclerLayout.visibility = View.VISIBLE
                Toast.makeText(
                    requireContext(),
                    getString(R.string.null_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Redirects to DetailedListing page by clicking on a specific Listing
    fun onItemClicked(listingID : Int) {
        findNavController().navigate(R.id.action_allListings_to_detailedListing,
            bundleOf("id" to listingID))
    }

    // Disable back button to increase intended usage of navigation bar
    private fun disableBackButton() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) { override fun handleOnBackPressed() {} }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
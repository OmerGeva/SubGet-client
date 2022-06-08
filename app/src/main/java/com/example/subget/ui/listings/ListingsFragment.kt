package com.example.subget.ui.listings

import android.app.AlertDialog
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subget.R
import com.example.subget.databinding.FragmentListingsBinding
import com.example.subget.utils.Error
import com.example.subget.utils.Loading
import com.example.subget.utils.Success

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
        activity?.setActionBar(activity?.findViewById(R.id.toolbar))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listingRecycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListingAdapter(this@ListingsFragment)
        binding.listingRecycler.adapter = adapter

        // if offline
        getSearchResults()
        getOnlineListings()

    }

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

    private fun getSearchResults(searchText: String) {
        var searchText = "%$searchText%"
        viewModel.viewModelGetSearchResults(location = searchText).observe(viewLifecycleOwner) {
            adapter.setListings(it)}
    }


    private fun getOnlineListings() {
        viewModel.listings.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> { binding.loadingScreen.visibility = View.VISIBLE }

                is Success -> {
                    if (it.status.data != null) {
                        binding.loadingScreen.visibility = View.GONE
                        binding.recyclerLayout.visibility = View.VISIBLE
                        adapter.setListings(it.status.data) }
                }

                is Error -> {
                    val ConnectionManager = ContextCompat.getSystemService(
                        requireContext(),
                        ConnectivityManager::class.java
                    ) as ConnectivityManager

                    val networkInfo = ConnectionManager.activeNetworkInfo
                    if (networkInfo == null || !networkInfo.isConnected) {
                        dialog("Ooops, it seems like we have an error...\n Please check your internet connection and restart the app")
                    } else{
                        dialog("Ooops, we've encountered the following error: " + it.status.message)
                    }                }
            }
        }
    }

    private fun getOfflineListings() {
        viewModel.offlineListings.observe(viewLifecycleOwner) { adapter.setListings(it) }
    }

    private fun dialog(message: String) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage(message)
        dialog.setPositiveButton("OK") { _, _ ->
            // Do something
        }
        dialog.setNegativeButton("NOT OK") { dialog, _ ->
            dialog.cancel()
        }
        dialog.create().show()
    }

    fun onItemClicked(listingID : Int) {
        findNavController().navigate(R.id.action_allListings_to_detailedListing,
            bundleOf("id" to listingID))
    }
}
package com.example.subget.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subget.R
import com.example.subget.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val viewModel : FavoritesViewModel by viewModels()
    private  lateinit var  adapter: FavoritesAdapter

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        activity?.setActionBar(activity?.findViewById(R.id.toolbar))
        binding.toolbarSearch.toolbar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createRecyclerView()
        getSearchResults()

    }


    private fun getSearchResults() {
        binding.toolbarSearch.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getItemsFromDb(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    getItemsFromDb(newText)
                }
                return true
            }
        })
    }

    private fun getItemsFromDb(searchText: String) {
        var searchText = searchText
        searchText = "%$searchText%"
        viewModel.searchForListings(location = searchText).observe(viewLifecycleOwner) { adapter.setFavorites(it)}
    }

    private fun createRecyclerView() {
        adapter = FavoritesAdapter(this)
        binding.favoritesRecycler.adapter = adapter
        binding.favoritesRecycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.favorites.observe(viewLifecycleOwner) { adapter.setFavorites(it) }
    }

    fun onListingClick(listingID : Int) {
        findNavController().navigate(R.id.action_favorites_to_detailedListing,
            bundleOf("id" to listingID)
        )
    }
}


package com.example.subget.ui.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subget.app_data.models.Listing
import com.example.subget.databinding.FragmentFavoritesBinding
import com.example.subget.ui.listings.ListingAdapter
import com.example.subget.utils.Error
import com.example.subget.utils.Loading
import com.example.subget.utils.Success
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : FavoritesViewModel by viewModels()
    private  lateinit var  adapter: FavoritesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoadRecyclerView()

        }

    fun LoadRecyclerView() {
        adapter = FavoritesAdapter(this)
        binding.favoritesRecycler.adapter = adapter
        binding.favoritesRecycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.favorites.observe(viewLifecycleOwner) { adapter.setFavorites(it) }
    }

    fun onListingClick(id: Int) {
        Toast.makeText(context, "fuck2", Toast.LENGTH_SHORT).show()
    }


}


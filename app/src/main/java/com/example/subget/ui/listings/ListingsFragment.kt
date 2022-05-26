package com.example.subget.ui.listings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subget.databinding.FragmentListingsBinding
import dagger.hilt.android.AndroidEntryPoint

const val BASE_URL = "https://subget.herokuapp.com/"

@AndroidEntryPoint
class ListingsFragment : Fragment() {

    private var _binding: FragmentListingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListingsBinding.inflate(inflater, container, false)
        binding.listingRecycler.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    fun onItemClicked(adapterPosition: Int) {
        //TODO: create the function for the onClick
        Toast.makeText(context, "fuck1", Toast.LENGTH_SHORT).show()
    }

    fun onItemLongClick(adapterPosition: Int) {
        //TODO: create the function for the onLongClick
        Toast.makeText(context, "fuck2", Toast.LENGTH_SHORT).show()
    }
}
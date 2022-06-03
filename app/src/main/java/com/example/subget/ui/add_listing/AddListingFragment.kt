package com.example.subget.ui.add_listing

import android.content.ClipData
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.subget.R
import com.example.subget.app_data.models.Listing
import com.example.subget.databinding.FragmentAddListingBinding
import com.example.subget.ui.favorites.FavoritesViewModel
import com.example.subget.utils.Constants.Companion.PLACE_HOLDER_IMAGE
import com.example.subget.utils.Loading
import com.example.subget.utils.Success
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Error

@AndroidEntryPoint
class AddListingFragment : Fragment() {

    private val viewModel : AddListingViewModel by viewModels()

    private var _binding: FragmentAddListingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddListingBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            with(viewModel) {
                addItem(
                    Listing(
                        id,
                        title = binding.title.text.toString(),
                        description = binding.description.text.toString(),
                        address = binding.address.text.toString(),
                        phone_number = binding.phoneNumber.text.toString(),
                        image = PLACE_HOLDER_IMAGE,
                        contact_name = binding.contactName.text.toString(),
                        washing_machine = binding.washingMachine.isChecked,
                        pet_allowed = binding.washingMachine.isChecked,
                        near_beach = binding.washingMachine.isChecked,
                        wifi = binding.washingMachine.isChecked,
                        bedrooms = Integer.parseInt(binding.bedrooms.text.toString()),
                        floor = Integer.parseInt(binding.floor.text.toString()),
                        price = Integer.parseInt(binding.price.text.toString()),
                        bathrooms = Integer.parseInt(binding.bathrooms.text.toString()),
                        favorite = 0
                    )
                )
            }
            viewModel.listingDetail?.observe(viewLifecycleOwner) {
                // navigate to the listing detail
                Log.d("it", it.toString())
                Log.d("stats", it.status.toString())
                when (it.status) {
                    is Success -> {
                        Log.d("success", "navigating to home")
                        val id = it.status.data!!.id
                        // TODO: NAVIGATE WITH action_addListings_to_detailedListing with bundleOf("id" to id)
                        findNavController().navigate(
                            R.id.action_addListings_to_allListings,
                            bundleOf("address" to "%"+binding.address.text+"%")
                        )
                    }
                    is Error -> {
                        Log.d("Error", "Error, please check logs.")
                    }
                    is Loading -> {
                        Log.d("Loading", "Error, please check logs.")
                    }
                }
            }
        }
        return binding.root
    }
}
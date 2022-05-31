package com.example.subget.ui.listing_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.subget.R
import com.example.subget.app_data.models.Listing
import com.example.subget.databinding.FragmentDetailsBinding
import com.example.subget.utils.Loading
import com.example.subget.utils.Success
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val viewModel : DetailsViewModel by viewModels()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("id")?.let { viewModel.setId(it) }
        viewModel.listing.observe(viewLifecycleOwner) { setListing(it) }
    }

    private fun setListing(listing: Listing) {
        binding.detailedTitle.text = listing.title
        binding.detailedAddress.text = listing.address
        binding.detailedDescription.text = listing.description
        binding.detailedNumOfRooms.text = listing.bedrooms.toString()
        binding.detailedPhone.text = listing.phone_number
        binding.detailedContactName.text = listing.contact_name
        binding.detailedPrice.text = "$" + listing.price.toString()
        Glide.with(requireContext()).load(listing.image).into(binding.detailedImage)
        setIcons(listing)
    }

    private fun setIcons(listing: Listing) {
        val icons = "icon1"

        if(listing.washing_machine) {
            binding.icon1.setImageDrawable(resources.getDrawable(R.drawable.washing_machine))
        }
        if(listing.pet_allowed) {
            binding.icon2.setImageDrawable(resources.getDrawable(R.drawable.pets_icon))
        }
        if(listing.wifi) {
            binding.icon3.setImageDrawable(resources.getDrawable(R.drawable.wifi_icon))
        }
        if(listing.near_beach) {
            binding.icon4.setImageDrawable(resources.getDrawable(R.drawable.beach))
        }
    }
}
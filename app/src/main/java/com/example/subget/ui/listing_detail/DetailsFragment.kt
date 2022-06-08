package com.example.subget.ui.listing_detail

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.subget.R
import com.example.subget.app_data.models.Listing
import com.example.subget.databinding.FragmentDetailsBinding
import com.example.subget.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


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

        val hasConnection = requireActivity().internetEnabled()

        arguments?.getInt("id")?.let {
            Log.d("OFFLINE TEST", it.toString())
            viewModel.setId(it) }


        binding.mapicon.setOnClickListener {
            if (hasConnection) { setCoordinates() }
            else { requireActivity().dialog(getString(R.string.internet_connection)) }
        }

        if (hasConnection) { getOnlineListing() }
        else { getOfflineListing() }

        binding.detailedPhone.setOnClickListener { call() }
        binding.heartIcon.setOnClickListener {

            if (binding.heartIcon.isSelected) {
                viewModel.viewModelDeleteFavorite()
            } else {
                viewModel.viewModelAddFavorite()
            }
            binding.heartIcon.isSelected = !binding.heartIcon.isSelected
        }
    }

    private fun getOnlineListing() {
        viewModel.listing.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> { binding.loadingScreen.visibility = View.VISIBLE }

                is Success -> {
                    if (it.status.data != null) {
                        binding.loadingScreen.visibility = View.GONE
                        binding.scrollView.visibility = View.VISIBLE
                        binding.detailedImage.visibility = View.VISIBLE
                        setListing(it.status.data)
                    }
                }

                is Error -> {
                        requireActivity().dialog(getString(R.string.error_dialog)
                                + it.status.message + getString(R.string.error_dialog_cont))
                }
            }
        }
    }

    private fun getOfflineListing() {
        viewModel.offlineListing.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.scrollView.visibility = View.VISIBLE
                binding.detailedImage.visibility = View.VISIBLE
                setListing(it)
            } else {
                Toast.makeText(requireContext(), getString(R.string.null_message), LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setListing(listing: Listing) {
        viewModel.favorite.observe(viewLifecycleOwner) { binding.heartIcon.isSelected = it }
        binding.detailedTitle.text = listing.title
        binding.detailedAddress.text = listing.address
        binding.detailedDescription.text = listing.description
        binding.detailedNumOfRooms.text = listing.bedrooms.toString()
        binding.detailedPhone.text = listing.phone_number
        binding.detailedContactName.text = listing.contact_name
        binding.detailedPrice.text = getString(R.string.dollar) + listing.price.toString()

        Glide.with(requireContext()).load(listing.image).into(binding.detailedImage)
        setIcons(listing)
    }

    private fun setCoordinates() {
        var sendLat: Double? = null; var sendLng: Double? = null
        try {
            val geocode = Geocoder(context, Locale.getDefault())
            val addList = geocode.getFromLocationName(binding.detailedAddress.text.toString(),1)
            sendLat = addList[0].latitude
            sendLng = addList[0].longitude

        }catch (e: Exception){
            Toast.makeText(context, getString(R.string.error_map), LENGTH_SHORT).show()
        }

        if((sendLng != null) && (sendLat != null)) {
            findNavController().navigate(R.id.action_navigation_details_to_mapFragment,
                bundleOf("lat" to sendLat,
                    "lng" to sendLng,
                    "title" to binding.detailedTitle.text,
                    "address" to binding.detailedAddress.text))
        }
    }

    private fun setIcons(listing: Listing) {
        if(listing.washing_machine) {
            binding.icon1.setImageResource(R.drawable.washing_machine)
        }
        if(listing.pet_allowed) {
            binding.icon2.setImageResource(R.drawable.pets_icon)
        }
        if(listing.wifi) {
            binding.icon3.setImageResource(R.drawable.wifi_icon)
        }
        if(listing.near_beach) {
            binding.icon4.setImageResource(R.drawable.beach)
        }
    }

    private fun call() {
        val phoneNumber = binding.detailedPhone.text.toString()
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
        startActivity(intent)
    }
}
package com.example.subget.ui.listing_detail

import android.app.AlertDialog
import android.location.Geocoder
import android.os.Bundle
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
import com.example.subget.utils.Error
import com.example.subget.utils.Loading
import com.example.subget.utils.Success
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

        getListing()


        binding.mapicon.setOnClickListener { setCoordinates() }

        arguments?.getInt("id")?.let { viewModel.setId(it) }



        binding.heartIcon.setOnClickListener {
            binding.heartIcon.isSelected = !binding.heartIcon.isSelected
            viewModel.viewModelUpdateFavorite()
        }
    }

    private fun getListing() {
        viewModel.listing.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.loadingScreen.visibility = View.VISIBLE
                }

                is Success -> {
                    binding.loadingScreen.visibility = View.GONE
                    binding.scrollView.visibility = View.VISIBLE
                    binding.detailedImage.visibility = View.VISIBLE
//                    setListing(it.status.data!!)
                    if (it.status.data == null) {
                        dialog("wtf")
                    } else {
                        setListing(it.status.data!!)
                    }
                }

                is Error -> {
                    dialog("Ooops, we've encountered the following error: " + it.status.message)
                }
            }
        }
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


    private fun setListing(listing: Listing) {
        binding.detailedTitle.text = listing.title
        binding.detailedAddress.text = listing.address
        binding.detailedDescription.text = listing.description
        binding.detailedNumOfRooms.text = listing.bedrooms.toString()
        binding.detailedPhone.text = listing.phone_number
        binding.detailedContactName.text = listing.contact_name
        binding.detailedPrice.text = "$" + listing.price.toString()
        binding.heartIcon.isSelected = listing.favorite
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
            Toast.makeText(context, "Listing isn't verified", LENGTH_SHORT).show()
        }


        if((sendLng != null) && (sendLat != null)) {
            findNavController().navigate(R.id.action_navigation_details_to_mapFragment,
                bundleOf("lat" to sendLat, "lng" to sendLng, "title" to (binding.detailedTitle.text)))
        }
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
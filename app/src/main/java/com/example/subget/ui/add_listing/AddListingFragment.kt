package com.example.subget.ui.add_listing

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.room.ColumnInfo.INTEGER
import com.example.subget.R
import com.example.subget.app_data.models.Listing
import com.example.subget.databinding.FragmentAddListingBinding
import com.example.subget.utils.Constants
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
            if (validateInput()) {
                sendListing(); receiveListing()
            }
        }

        return binding.root
    }

    var tempID = INTEGER

    private fun sendListing() {
        viewModel.setListing(
            Listing(

                title = binding.title.text.toString(),
                description = binding.description.text.toString(),
                address = binding.address.text.toString(),
                phone_number = binding.phoneNumber.text.toString(),
                contact_name = binding.contactName.text.toString(),
                washing_machine = binding.washingMachine.isChecked,
                pet_allowed = binding.washingMachine.isChecked,
                near_beach = binding.washingMachine.isChecked,
                wifi = binding.washingMachine.isChecked,
                bedrooms = Integer.parseInt(binding.bedrooms.text.toString()),
                floor = Integer.parseInt(binding.floor.text.toString()),
                price = Integer.parseInt(binding.price.text.toString()),
                bathrooms = Integer.parseInt(binding.bathrooms.text.toString()),
                image = PLACE_HOLDER_IMAGE,
                favorite = false
            )
        )
    }

    private fun receiveListing() {
        viewModel.newListing.observe(viewLifecycleOwner) {
            when (it.status) {
                is Success -> {
                        dialog("Successfully uploaded your Listing!", it.status.data!!.id)
                    binding.logo.visibility = View.VISIBLE
                    binding.uploadProgress.visibility = View.GONE
                }
                is Error -> {
                    dialog("Ooops, we've encountered the following error: " + it.status.message, -1)
                    binding.logo.visibility = View.VISIBLE
                    binding.uploadProgress.visibility = View.GONE
                }

                is Loading -> {
                    binding.logo.visibility = View.GONE
                    binding.uploadProgress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun dialog(message: String, id: Int) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage(message)
        dialog.setPositiveButton("OK") { _, _ ->
            if (id != -1) {
                findNavController().navigate(R.id.action_addListings_to_detailedListing,
                    bundleOf("id" to id))

            }
        }
        dialog.setNegativeButton("NOT OK") { dialog, _ ->
            dialog.cancel()
        }
        dialog.create().show()
    }

    private fun validateInput(): Boolean {

        var inputApproved = true

        if (TextUtils.isEmpty(binding.title.text.toString())) {
            binding.title.error = "Must fill in title!"
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.description.text.toString())) {
            binding.description.error = "Must fill in description!"
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.address.text.toString())) {
            binding.address.error = "Must fill in address!"
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.floor.text.toString())) {
            binding.floor.error = "Must fill in this option!"
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.bathrooms.text.toString())) {
            binding.bathrooms.error = "Must fill in this option!"
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.price.text.toString())) {
            binding.price.error = "Must fill in price!"
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.contactName.text.toString())) {
            binding.contactName.error = "Must fill in name!"
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.phoneNumber.text.toString())) {
            binding.phoneNumber.error = "Must fill in phone number!"
            inputApproved = false
        }

        return inputApproved
    }
}
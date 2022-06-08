package com.example.subget.ui.add_listing

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.subget.R
import com.example.subget.app_data.models.Listing
import com.example.subget.databinding.FragmentAddListingBinding
import com.example.subget.utils.Constants.Companion.PLACE_HOLDER_IMAGE
import com.example.subget.utils.Loading
import com.example.subget.utils.Success
import com.example.subget.utils.dialog
import com.example.subget.utils.internetEnabled
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
        disableBackButton()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hasConnection = requireActivity().internetEnabled()


        binding.submitButton.setOnClickListener {
            if (hasConnection) {
                binding.submitButton.isClickable = false
                if (validateInput()) {
                    sendListing(); receiveListing()
//                    binding.submitButton.postDelayed({binding.submitButton.isClickable = true},2000)
                }
            } else { requireActivity().dialog(getString(R.string.internet_connection)) }
        }
    }

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
                image = PLACE_HOLDER_IMAGE
            )
        )
    }

    private fun receiveListing() {
        viewModel.newListing.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.logo.visibility = View.GONE
                    binding.uploadProgress.visibility = View.VISIBLE
                }

                is Success -> {
                    if (it.status.data != null) {
                        dialogRedirect(getString(R.string.success), it.status.data.id)
                        clearSelection()
                        binding.logo.visibility = View.VISIBLE
                        binding.uploadProgress.visibility = View.GONE
                    }
                }

                is Error -> {
                    requireActivity().dialog(getString(R.string.error_dialog)
                            + it.status.message + getString(R.string.error_dialog_cont))
                    binding.logo.visibility = View.VISIBLE
                    binding.uploadProgress.visibility = View.GONE
                }
            }
        }
    }

    private fun dialogRedirect(message: String, id: Int) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage(message)
        dialog.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            findNavController().navigate(R.id.action_addListings_to_detailedListing, bundleOf("id" to id))
            binding.submitButton.isClickable = true
            dialog.cancel()
        }
        dialog.create().show()
    }

    private fun clearSelection() {
        binding.title.text.clear()
        binding.description.text.clear()
        binding.address.text.clear()
        binding.phoneNumber.text.clear()
        binding.contactName.text.clear()
        binding.bathrooms.text.clear()
        binding.floor.text.clear()
        binding.price.text.clear()
        binding.bedrooms.text.clear()
        binding.petAllowed.isChecked = false
        binding.wifi.isChecked = false
        binding.nearBeach.isChecked = false
        binding.washingMachine.isChecked = false
    }

    private fun validateInput(): Boolean {

        var inputApproved = true

        if (TextUtils.isEmpty(binding.title.text.toString())) {
            binding.title.error = getString(R.string.fill_title)
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.description.text.toString())) {
            binding.description.error = getString(R.string.fill_description)
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.address.text.toString())) {
            binding.address.error = getString(R.string.fill_address)
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.floor.text.toString())) {
            binding.floor.error = getString(R.string.fill_floor)
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.bathrooms.text.toString())) {
            binding.bathrooms.error = getString(R.string.fill_bathroom)
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.price.text.toString())) {
            binding.price.error = getString(R.string.fill_price)
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.contactName.text.toString())) {
            binding.contactName.error = getString(R.string.fill_contact)
            inputApproved = false
        }
        if (TextUtils.isEmpty(binding.phoneNumber.text.toString())) {
            binding.phoneNumber.error = getString(R.string.fill_phone)
            inputApproved = false
        }
        return inputApproved
    }

    private fun disableBackButton() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) { override fun handleOnBackPressed() {} }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
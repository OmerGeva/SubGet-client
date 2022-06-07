package com.example.subget.ui.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.subget.R
import com.example.subget.app_data.models.Stats
import com.example.subget.databinding.FragmentDetailsBinding
import com.example.subget.databinding.FragmentHomeBinding
import com.example.subget.ui.listings.ListingsViewModel
import com.example.subget.utils.Error
import com.example.subget.utils.Loading
import com.example.subget.utils.Success

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel : HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()

    }

    private fun fetchData() {
        viewModel.stats.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> {
                    binding.loadingScreen.visibility = View.VISIBLE


                }

                is Success -> {
                    binding.loadingScreen.visibility = View.GONE
                    binding.LL.visibility = View.VISIBLE
                    if (it.status.data != null) {
                        setStats(it.status.data!!)
                    } else {
                        binding.count.text = "null"
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

    private fun setStats(stats: Stats) {
        binding.avg.text = stats.listings_price_avg.toString()
        binding.max.text = stats.listings_price_max.toString()
        binding.min.text = stats.listings_price_min.toString()
    }
}
package com.example.subget.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.subget.app_data.models.Stats
import com.example.subget.databinding.FragmentHomeBinding
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

        disableBackButton()
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

                    if (it.status.data != null) {
                        binding.LL.visibility = View.VISIBLE
                        setStats(it.status.data!!)
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
        binding.mostExpensive.text = stats.most_expensive_address
        binding.leastExpensive.text = stats.least_expensive_address
        binding.count.text = stats.listings_count.toString()

    }

    private fun disableBackButton() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,  // LifecycleOwner
            callback
        )

    }



}
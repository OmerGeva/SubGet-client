package com.example.subget.ui.home

import android.app.AlertDialog
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.subget.R
import com.example.subget.app_data.models.Stats
import com.example.subget.databinding.FragmentHomeBinding
import com.example.subget.utils.*
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

        // Disable back button to increase intended usage of navigation bar
        disableBackButton()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Checks whether user has internet connection
        val hasConnection = requireActivity().internetEnabled()

        // Populates page with data according to Online/Offline mode
        if (hasConnection) { getOnlineStats() }
        else { getOfflineStats() }

    }
    // Populates page with data from local and remote databases
    private fun getOnlineStats() {
        viewModel.stats.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> { binding.loadingScreen.visibility = View.VISIBLE }

                is Success -> {
                    binding.loadingScreen.visibility = View.GONE
                    if (it.status.data != null) {
                        binding.dataDisplay.visibility = View.VISIBLE
                        setStats(it.status.data)
                    }
                }

                is Error -> {
                    requireActivity().dialog(getString(R.string.error_dialog)
                            + it.status.message + getString(R.string.error_dialog_cont))
                }
            }
        }
    }

    // Populates page with data from local database
    private fun getOfflineStats() {
        viewModel.offlineStats.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.dataDisplay.visibility = View.VISIBLE
                setStats(it)
            } else {
                Toast.makeText(requireContext(), getString(R.string.null_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Displays Stat values on page
    private fun setStats(stats: Stats) {
        binding.avg.text = "$" + stats.listings_price_avg.toString()
        binding.max.text = "$" + stats.listings_price_max.toString()
        binding.min.text = "$" + stats.listings_price_min.toString()
        binding.mostExpensive.text = stats.most_expensive_address
        binding.leastExpensive.text = stats.least_expensive_address
        binding.count.text = stats.listings_count.toString()
    }

    // Disable back button to increase intended usage of navigation bar
    private fun disableBackButton() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) { override fun handleOnBackPressed() {} }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
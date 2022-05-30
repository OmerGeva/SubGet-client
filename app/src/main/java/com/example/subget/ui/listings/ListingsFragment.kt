package com.example.subget.ui.listings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subget.R
import com.example.subget.databinding.FragmentListingsBinding
import com.example.subget.utils.Error
import com.example.subget.utils.Loading
import com.example.subget.utils.Success
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListingsFragment : Fragment() {

    private var _binding: FragmentListingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ListingsViewModel by viewModels()
    private  lateinit var  adapter: ListingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListingsBinding.inflate(inflater, container, false)
        binding.listingRecycler.layoutManager = LinearLayoutManager(requireContext())
        fetchData()

        return binding.root
    }

//    private fun getMyData() {
//        val retrofitBuilder = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()
//            .create(ApiInterface::class.java)
//
//        val retrofitData = retrofitBuilder.getData()
//
//        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
//            override fun onResponse(
//                call: Call<List<MyDataItem>?>,
//                response: Response<List<MyDataItem>?>
//            ) {
//                val responseBody = response.body()!!
//
//                val Datalist : MutableList<MyDataItem> = ArrayList()
//                for(listing in responseBody) {
//                    val dataItem = MyDataItem(listing.id, listing.title, listing.description, listing.address, listing.phone_number, listing.image, listing.washing_machine, listing.pet_allowed, listing.price)
//                    Datalist.add(dataItem)
//                }
//                binding.listingRecycler.adapter = ListingAdapter(Datalist,this@ListingsFragment)
//            }
//
//            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
//                Log.d("MainActivity", "onFailure: "+t.message)
//            }
//        })
//    }

    fun fetchData() {

        adapter = ListingAdapter(this@ListingsFragment)
        binding.listingRecycler.adapter = adapter

        viewModel.listings.observe(viewLifecycleOwner) {
            when (it.status) {
                is Loading -> print("loading")

                is Success -> {
                    adapter.setListings(it.status.data!!)
                }

                is Error -> {
                    print(false)
                }
            }


        }
    }

    fun onItemClicked(listingID : Int) {
        findNavController().navigate(R.id.action_allListings_to_detailedListing,
            bundleOf("id" to listingID))
    }

    fun onItemLongClick(adapterPosition: Int) {
        //TODO: create the function for the onLongClick
        print(false)
    }
}
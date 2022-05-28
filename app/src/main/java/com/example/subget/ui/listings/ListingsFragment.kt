package com.example.subget.ui.listings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subget.databinding.FragmentListingsBinding
import com.example.subget.utils.Error
import com.example.subget.utils.Loading
import com.example.subget.utils.Success
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
                is Loading -> Toast.makeText(requireContext(), "PLACE HOLDER", Toast.LENGTH_LONG)
                    .show()

                is Success -> {
                    adapter.setListings(it.status.data!!)
                }

                is Error -> {
                    Toast.makeText(requireContext(), it.status.message, Toast.LENGTH_LONG).show()
                }
            }


        }
    }

    fun onItemClicked(adapterPosition: Int) {
        //TODO: create the function for the onClick
        Toast.makeText(context, "fuck1", Toast.LENGTH_SHORT).show()
    }

    fun onItemLongClick(adapterPosition: Int) {
        //TODO: create the function for the onLongClick
        Toast.makeText(context, "fuck2", Toast.LENGTH_SHORT).show()
    }
}
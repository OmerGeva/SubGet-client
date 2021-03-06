package com.example.subget.ui.listings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.subget.app_data.models.Listing
import com.example.subget.databinding.CardLayoutBinding

class ListingAdapter(private val callback: ListingsFragment) : RecyclerView.Adapter<ListingAdapter.ListingViewHolder>() {

    private val listings = ArrayList<Listing>()

    fun setListings(listings : Collection<Listing>) {
        this.listings.clear()
        this.listings.addAll(listings)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListingViewHolder(
            CardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) =
        holder.bind(listings[position])

    override fun getItemCount() = listings.size

    inner class ListingViewHolder(private val binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private lateinit var listing: Listing

        init { binding.root.setOnClickListener(this) }

        fun bind(listing: Listing) {
            this.listing = listing
            Glide.with(callback).load(listing.image).into(binding.cardImage)
            binding.cardTitle.text = listing.title
            binding.cardAddress.text = listing.address
            binding.cardPrice.text = "$" + listing.price.toString()
        }

        override fun onClick(v: View?) {
            callback.onItemClicked(listing.id)
        }
    }
}

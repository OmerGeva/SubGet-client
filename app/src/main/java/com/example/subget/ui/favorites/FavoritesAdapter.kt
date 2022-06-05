package com.example.subget.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.subget.app_data.models.Listing
import com.example.subget.databinding.CardLayoutBinding

class FavoritesAdapter(private val listener : FavoritesFragment) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    var favorites = ArrayList<Listing>()

    fun setFavorites(favorites : Collection<Listing>) {
        this.favorites.clear()
        this.favorites.addAll(favorites)
        notifyDataSetChanged()
    }

    class FavoritesViewHolder(private val itemBinding: CardLayoutBinding,
                              private val listener: FavoritesFragment)
        : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var listing: Listing

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Listing) {
            this.listing = item
            itemBinding.cardTitle.text = listing.title
            itemBinding.cardAddress.text = listing.address
            itemBinding.cardPrice.text = listing.price.toString() + " $"
            Glide.with(itemBinding.root)
                .load(item.image)
                .into(itemBinding.cardImage)
        }

        override fun onClick(v: View?) {
            listener.onListingClick(listing.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoritesViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) =
        holder.bind(favorites[position])


    override fun getItemCount() = favorites.size

    interface ListingListener {
        fun onListingClick(listingID : Int)
    }
}
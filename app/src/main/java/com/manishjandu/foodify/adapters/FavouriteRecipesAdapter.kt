package com.manishjandu.foodify.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manishjandu.foodify.adapters.FavouriteRecipesAdapter.FavouriteRecipeViewHolder
import com.manishjandu.foodify.data.database.entities.FavouriteEntity
import com.manishjandu.foodify.databinding.RowLayoutFavouriteBinding
import com.manishjandu.foodify.ui.fragments.favourites.FavouriteRecipesFragmentDirections


class FavouriteRecipesAdapter :
    ListAdapter<FavouriteEntity, FavouriteRecipeViewHolder>(DiffUtilCallback()) {

    class FavouriteRecipeViewHolder(private val binding: RowLayoutFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
         val favouriteRecipeRowLayout = binding.favouriteRecipeRowLayout

        fun bind(favouriteEntity: FavouriteEntity) {
            binding.favouritesEntity = favouriteEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavouriteRecipeViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutFavouriteBinding.inflate(inflater, parent, false)
                return FavouriteRecipeViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRecipeViewHolder {
        return FavouriteRecipeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavouriteRecipeViewHolder, position: Int) {
        val currentRecipe = getItem(position)
        holder.bind(currentRecipe)
        /**
         * Single click listener
         * */
        holder.favouriteRecipeRowLayout.setOnClickListener {
            val action =
                FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToDetailsActivity(
                    currentRecipe.result
                )
            holder.itemView.findNavController().navigate(action)
        }
    }

    class DiffUtilCallback() : DiffUtil.ItemCallback<FavouriteEntity>() {
        override fun areItemsTheSame(oldItem: FavouriteEntity, newItem: FavouriteEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: FavouriteEntity,
            newItem: FavouriteEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

}
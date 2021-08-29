package com.manishjandu.foodify.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manishjandu.foodify.R
import com.manishjandu.foodify.adapters.FavouriteRecipesAdapter.FavouriteRecipeViewHolder
import com.manishjandu.foodify.data.database.entities.FavouriteEntity
import com.manishjandu.foodify.databinding.RowLayoutFavouriteBinding
import com.manishjandu.foodify.ui.fragments.favourites.FavouriteRecipesFragmentDirections


class FavouriteRecipesAdapter(
    private val requireActivity: FragmentActivity
) :
    ListAdapter<FavouriteEntity, FavouriteRecipeViewHolder>(DiffUtilCallback()),
    ActionMode.Callback {

    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavouriteEntity>()
    private var viewHolders = arrayListOf<FavouriteRecipeViewHolder>()

    class FavouriteRecipeViewHolder(private val binding: RowLayoutFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val favouriteRecipeRowLayout = binding.favouriteRecipeRowLayout
        val cardViewRow = binding.cardViewRow

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
        viewHolders.add(holder)
        val currentRecipe = getItem(position)
        holder.bind(currentRecipe)
        /**
         * Single click listener
         * */
        holder.favouriteRecipeRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavouriteRecipesFragmentDirections.actionFavouriteRecipesFragmentToDetailsActivity(
                        currentRecipe.result
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }
        /**
         *Long click listener
         */
        holder.favouriteRecipeRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder,currentRecipe)
                true
            }else{
                multiSelection = false
                false
            }
        }
    }

    private fun applySelection(holder: FavouriteRecipeViewHolder, currentRecipe: FavouriteEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackGroundColor, R.color.strokeColor)
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackGroundLightColor, R.color.colorPrimary)
        }
    }

    private fun changeRecipeStyle(
        holder: FavouriteRecipeViewHolder,
        backgroundColor: Int,
        strokeColor: Int
    ) {
        holder.favouriteRecipeRowLayout.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity,
                backgroundColor
            )
        )
        holder.cardViewRow.strokeColor = ContextCompat.getColor(requireActivity, strokeColor)
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favourite_contextual_menu, menu)
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        viewHolders.forEach { holder->
            changeRecipeStyle(holder, R.color.cardBackGroundColor, R.color.strokeColor)
        }
        multiSelection =false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
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
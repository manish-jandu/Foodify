package com.manishjandu.foodify.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manishjandu.foodify.adapters.FavouriteRecipesAdapter
import com.manishjandu.foodify.data.database.entities.FavouriteEntity

class FavouriteRecipeBinding {
    companion object {

        @BindingAdapter("viewVisibility","setDataInRecyclerView", requireAll = false)
        @JvmStatic
        fun viewVisibilityWithData(
            view: View,
            favoritesEntities: List<FavouriteEntity>?,
            favouriteRecipesAdapter: FavouriteRecipesAdapter?
        ) {
            if (favoritesEntities.isNullOrEmpty()) {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.INVISIBLE
                    }
                }
            } else {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        favouriteRecipesAdapter?.submitList(favoritesEntities)
                    }
                }
            }
        }

    }
}
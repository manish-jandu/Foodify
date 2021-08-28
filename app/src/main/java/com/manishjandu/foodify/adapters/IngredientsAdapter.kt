package com.manishjandu.foodify.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.manishjandu.foodify.R
import com.manishjandu.foodify.databinding.IngredientsRowLayoutBinding
import com.manishjandu.foodify.models.ExtendedIngredient
import com.manishjandu.foodify.util.Constants.Companion.BASE_IMAGE_URL

class IngredientsAdapter :
    ListAdapter<ExtendedIngredient, IngredientsAdapter.IngredientsViewHolder>(DiffUtilCallback()) {
    class IngredientsViewHolder(binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val ingredientImage = binding.imageViewIngredients
        private val ingredientName = binding.textViewIngredientName
        private val ingredientAmount = binding.textViewIngredientAmount
        private val ingredientUnit = binding.textViewIngredientUnit
        private val ingredientConsistency = binding.textViewConsistency
        private val ingredientOriginal = binding.textViewOriginal

        fun bind(currentIngredient: ExtendedIngredient) {
            ingredientImage.load(BASE_IMAGE_URL + currentIngredient.image){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            ingredientName.text = currentIngredient.name.replaceFirstChar {
                it.uppercase()
            }
            ingredientAmount.text = currentIngredient.amount.toString()
            ingredientUnit.text = currentIngredient.unit
            ingredientConsistency.text = currentIngredient.consistency
            ingredientOriginal.text = currentIngredient.original
        }

        companion object {
            fun from(parent: ViewGroup): IngredientsViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = IngredientsRowLayoutBinding.inflate(inflater, parent, false)
                return IngredientsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val currentIngredient = getItem(position)
        currentIngredient?.let {
            holder.bind(it)
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<ExtendedIngredient>() {
        override fun areItemsTheSame(
            oldItem: ExtendedIngredient,
            newItem: ExtendedIngredient
        ): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: ExtendedIngredient,
            newItem: ExtendedIngredient
        ): Boolean {
            return oldItem == newItem
        }
    }
}

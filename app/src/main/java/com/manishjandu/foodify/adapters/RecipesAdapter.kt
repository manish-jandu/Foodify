package com.manishjandu.foodify.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manishjandu.foodify.databinding.RecipeRowLayoutBinding
import com.manishjandu.foodify.models.Result

class RecipesAdapter : ListAdapter<Result,RecipesAdapter.RecipeViewHolder>(DiffUtilCallback()) {

    class RecipeViewHolder(private val binding: RecipeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):RecipeViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = RecipeRowLayoutBinding.inflate(inflater,parent,false)
                return RecipeViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = getItem(position)
        holder.bind(currentRecipe)
    }

    class DiffUtilCallback():DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
           return oldItem == newItem
        }

    }

}
package com.manishjandu.foodify.ui.fragments.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.manishjandu.foodify.R
import com.manishjandu.foodify.adapters.FavouriteRecipesAdapter
import com.manishjandu.foodify.databinding.FragmentFavouriteRecipesBinding
import com.manishjandu.foodify.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteRecipesFragment : Fragment(R.layout.fragment_favourite_recipes) {
    private var _binding: FragmentFavouriteRecipesBinding? = null
    private val binding get() = _binding!!
    private val favouriteRecipesAdapter by lazy { FavouriteRecipesAdapter(requireActivity()) }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouriteRecipesBinding.bind(view)

        setupRecyclerView()

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.favouriteRecipeAdapter = favouriteRecipesAdapter

    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerView.adapter = favouriteRecipesAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
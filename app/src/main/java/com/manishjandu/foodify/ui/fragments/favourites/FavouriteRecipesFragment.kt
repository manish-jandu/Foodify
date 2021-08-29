package com.manishjandu.foodify.ui.fragments.favourites

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.manishjandu.foodify.R
import com.manishjandu.foodify.adapters.FavouriteRecipesAdapter
import com.manishjandu.foodify.databinding.FragmentFavouriteRecipesBinding
import com.manishjandu.foodify.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteRecipesFragment : Fragment(R.layout.fragment_favourite_recipes) {
    private var _binding: FragmentFavouriteRecipesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private val favouriteRecipesAdapter by lazy {
        FavouriteRecipesAdapter(
            requireActivity(),
            mainViewModel
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouriteRecipesBinding.bind(view)

        setupRecyclerView()

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.favouriteRecipeAdapter = favouriteRecipesAdapter

        setHasOptionsMenu(true)
    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerView.adapter = favouriteRecipesAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favourite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_favourite_recipes) {
            mainViewModel.deleteAllFavouriteRecipes()
            showSnackBar("All Recipe Removed.")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            requireView(),
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        favouriteRecipesAdapter.clearContextualMode()
    }
}
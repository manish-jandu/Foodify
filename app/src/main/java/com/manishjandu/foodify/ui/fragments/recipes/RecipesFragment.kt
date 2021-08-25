package com.manishjandu.foodify.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.manishjandu.foodify.R
import com.manishjandu.foodify.adapters.RecipesAdapter
import com.manishjandu.foodify.databinding.FragmentRecipesBinding
import com.manishjandu.foodify.util.NetworkResult.*
import com.manishjandu.foodify.util.observeOnce
import com.manishjandu.foodify.viewmodels.MainViewModel
import com.manishjandu.foodify.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment(R.layout.fragment_recipes) {
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val recipeViewModel: RecipesViewModel by viewModels()
    private val recipesAdapter by lazy { RecipesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipesBinding.bind(view)

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        binding.floatingActionButtonRecipes.setOnClickListener {
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
        }

        showRecyclerView()
        readDatabase()
    }

    private fun showRecyclerView() {
        binding.recyclerView.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabase called!")
                    recipesAdapter.submitList(database[0].foodRecipe.results)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData called!")
        mainViewModel.getRecipes(recipeViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        recipesAdapter.submitList(it.results)
                    }
                }
                is Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    recipesAdapter.submitList(database[0].foodRecipe.results)
                }
            }
        }
    }

    private fun showShimmerEffect() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerView.hideShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
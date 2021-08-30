package com.manishjandu.foodify.ui.fragments.foodjoke

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.manishjandu.foodify.R
import com.manishjandu.foodify.databinding.FragmentFoodJokeBinding
import com.manishjandu.foodify.util.Constants.Companion.API_KEY
import com.manishjandu.foodify.util.NetworkResult
import com.manishjandu.foodify.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodJokeFragment : Fragment(R.layout.fragment_food_joke) {
    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFoodJokeBinding.bind(view)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.textViewFoodJoke.text = response.data?.text
                }
                is NetworkResult.Loading -> {
                    loadDataFromCache()
                    Log.d("FoodJokeFragment", "network result loading")
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && database != null) {
                    binding.textViewFoodJoke.text = database[0].foodJoke.text
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
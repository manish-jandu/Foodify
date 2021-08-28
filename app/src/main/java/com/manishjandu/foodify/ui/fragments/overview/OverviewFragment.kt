package com.manishjandu.foodify.ui.fragments.overview

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.manishjandu.foodify.R
import com.manishjandu.foodify.databinding.FragmentOverviewBinding
import com.manishjandu.foodify.models.Result
import com.manishjandu.foodify.util.Constants.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup

class OverviewFragment: Fragment(R.layout.fragment_overview) {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOverviewBinding.bind(view)

        val args = arguments
        val resultBundle : Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        binding.apply {
            imageViewRecipeDetail.load(resultBundle?.image)
            textViewTitle.text = resultBundle?.title
            textViewLike.text = resultBundle?.aggregateLikes.toString()
            textViewTime.text = resultBundle?.readyInMinutes.toString()

            val summary = Jsoup.parse(resultBundle?.summary).text()
            textViewSummary.text = summary
        }


        val colorGreen = ContextCompat.getColor(requireContext(),R.color.green)

        if(resultBundle?.vegetarian == true){
            binding.apply {
                imageViewVegetarian.setColorFilter(colorGreen)
                textViewVegetarian.setTextColor(colorGreen)
            }
        }
        if(resultBundle?.vegan == true){
            binding.apply {
                imageViewVegan.setColorFilter(colorGreen)
                textViewVegan.setTextColor(colorGreen)
            }
        }
        if(resultBundle?.glutenFree == true){
            binding.apply {
                imageViewGlutenFree.setColorFilter(colorGreen)
                textViewGlutenFree.setTextColor(colorGreen)
            }
        }
        if(resultBundle?.cheap == true){
            binding.apply {
                imageViewCheap.setColorFilter(colorGreen)
                textViewCheap.setTextColor(colorGreen)
            }
        }
        if(resultBundle?.veryHealthy == true){
            binding.apply {
                imageViewHealthy.setColorFilter(colorGreen)
                textViewHealthy.setTextColor(colorGreen)
            }
        }
        if(resultBundle?.dairyFree == true){
            binding.apply {
                imageViewDairyFree.setColorFilter(colorGreen)
                textViewDairyFree.setTextColor(colorGreen)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
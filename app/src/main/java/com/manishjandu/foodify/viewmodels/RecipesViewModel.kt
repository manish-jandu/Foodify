package com.manishjandu.foodify.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.manishjandu.foodify.util.Constants
import com.manishjandu.foodify.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.manishjandu.foodify.util.Constants.Companion.QUERY_API_KEY
import com.manishjandu.foodify.util.Constants.Companion.QUERY_DIET
import com.manishjandu.foodify.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.manishjandu.foodify.util.Constants.Companion.QUERY_NUMBER
import com.manishjandu.foodify.util.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

}
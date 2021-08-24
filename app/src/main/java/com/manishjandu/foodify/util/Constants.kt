package com.manishjandu.foodify.util

import com.manishjandu.foodify.BuildConfig

class Constants {
    companion object{
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://api.spoonacular.com"

        //API key queries
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_NUMBER = "number"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"
    }
}
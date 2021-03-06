package com.manishjandu.foodify.data

import com.manishjandu.foodify.data.network.FoodRecipesApi
import com.manishjandu.foodify.models.FoodJoke
import com.manishjandu.foodify.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }
    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.searchRecipe(searchQuery)
    }

    suspend fun getFoodJoke(apiKey:String): Response<FoodJoke> {
        return foodRecipesApi.getFoodJoke(apiKey)
    }
}
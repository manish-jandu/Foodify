package com.manishjandu.foodify.data

import com.manishjandu.foodify.data.database.RecipesDao
import com.manishjandu.foodify.data.database.entities.FavouriteEntity
import com.manishjandu.foodify.data.database.entities.FoodJokeEntity
import com.manishjandu.foodify.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity) {
        recipesDao.insertFavouriteRecipe(favouriteEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    fun readFavouriteRecipes(): Flow<List<FavouriteEntity>> {
        return recipesDao.readFavouriteRecipes()
    }

    fun readFoodJoke(): Flow<List<FoodJokeEntity>> {
        return recipesDao.readFoodJoke()
    }

    suspend fun deleteFavouriteRecipe(favouriteEntity: FavouriteEntity) {
        recipesDao.deleteFavouriteRecipe(favouriteEntity)
    }

    suspend fun deleteAllFavouriteRecipes() {
        recipesDao.deleteAllFavouriteRecipes()
    }
}
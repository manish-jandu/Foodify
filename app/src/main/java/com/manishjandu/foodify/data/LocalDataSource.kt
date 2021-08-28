package com.manishjandu.foodify.data

import com.manishjandu.foodify.data.database.RecipesDao
import com.manishjandu.foodify.data.database.entities.FavouriteEntity
import com.manishjandu.foodify.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity) {
        recipesDao.insertFavouriteRecipe(favouriteEntity)
    }

    fun readFavouriteRecipes(): Flow<List<FavouriteEntity>> {
        return recipesDao.readFavouriteRecipes()
    }

    suspend fun deleteFavouriteRecipe(favouriteEntity: FavouriteEntity) {
        recipesDao.deleteFavouriteRecipe(favouriteEntity)
    }

    suspend fun deleteAllFavouriteRecipes() {
        recipesDao.deleteAllFavouriteRecipes()
    }
}
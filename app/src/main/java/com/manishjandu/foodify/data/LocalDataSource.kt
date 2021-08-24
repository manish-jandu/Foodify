package com.manishjandu.foodify.data

import com.manishjandu.foodify.data.database.RecipesDao
import com.manishjandu.foodify.data.database.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()

    }

}
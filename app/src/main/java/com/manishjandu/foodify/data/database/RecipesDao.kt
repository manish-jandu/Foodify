package com.manishjandu.foodify.data.database

import androidx.room.*
import com.manishjandu.foodify.data.database.entities.FavouriteEntity
import com.manishjandu.foodify.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity)

    @Query("SELECT * FROM favourite_recipe_table ORDER BY id ASC")
    fun readFavouriteRecipes():Flow<List<FavouriteEntity>>

    @Delete
    suspend fun deleteFavouriteRecipe(favouriteEntity: FavouriteEntity)

    @Query("DELETE FROM favourite_recipe_table")
    suspend fun deleteAllFavouriteRecipes()
}
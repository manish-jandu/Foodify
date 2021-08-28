package com.manishjandu.foodify.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manishjandu.foodify.data.database.entities.FavouriteEntity
import com.manishjandu.foodify.data.database.entities.RecipesEntity

@Database(entities = [RecipesEntity::class,FavouriteEntity::class], version = 1, exportSchema = false)
@TypeConverters(RecipesTypeConvertor::class)
abstract class RecipesDatabase:RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}
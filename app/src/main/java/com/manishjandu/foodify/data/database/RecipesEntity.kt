package com.manishjandu.foodify.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manishjandu.foodify.models.FoodRecipe
import com.manishjandu.foodify.util.Constants.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RecipesEntity(var foodRecipe: FoodRecipe) {
    @PrimaryKey
    var id: Int = 0
}
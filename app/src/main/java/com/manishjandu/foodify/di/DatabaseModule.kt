package com.manishjandu.foodify.di

import android.content.Context
import androidx.room.Room
import com.manishjandu.foodify.data.database.RecipesDatabase
import com.manishjandu.foodify.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context:Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun providesDao(recipesDatabase: RecipesDatabase) = recipesDatabase.recipesDao()

}
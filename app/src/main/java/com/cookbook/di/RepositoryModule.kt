package com.cookbook.di

import com.cookbook.network.RecipeService
import com.cookbook.network.model.RecipeDtoMapper
import com.cookbook.domain.boundaries.RecipeRepository
import com.cookbook.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            recipeService,
            recipeDtoMapper
        )
    }
}
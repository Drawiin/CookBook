package com.cookbook.repository

import com.cookbook.domain.boundaries.RecipeRepository
import com.cookbook.domain.model.Recipe
import com.cookbook.network.RecipeService
import com.cookbook.network.model.RecipeDtoMapper

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return recipeService.search(
            token,
            page,
            query
        ).results?.let { mapper.toDomainList(it) } ?: emptyList()
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return recipeService.get(token, id).let { mapper.mapToDomainModel(it) }
    }
}
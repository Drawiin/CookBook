package com.cookbook.domain.interactors

import com.cookbook.domain.boundaries.RecipeRepository
import com.cookbook.domain.model.Recipe
import javax.inject.Inject

class SearchRecipe @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend fun execute(token: String, page: Int, query: String): List<Recipe> {
        return repository.search(token, page, query)
    }
}
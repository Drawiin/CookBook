package com.cookbook.domain.interactors

import com.cookbook.domain.boundaries.RecipeRepository
import com.cookbook.domain.model.Recipe
import javax.inject.Inject

class GetRecipeDetails @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend fun execute(token: String, id: Int): Recipe {
        return repository.get(token, id)
    }
}
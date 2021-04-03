package com.cookbook.domain.boundaries

import android.app.DownloadManager
import com.cookbook.domain.model.Recipe

interface RecipeRepository {

    suspend fun search(token: String, page: Int, query: String): List<Recipe>

    suspend fun get(token: String, id: Int): Recipe
}
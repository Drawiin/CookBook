package com.cookbook.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookbook.domain.model.Recipe
import com.cookbook.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import com.cookbook.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
import com.cookbook.repository.RecipeRepository
import com.cookbook.util.NAMED_TOKEN
import com.cookbook.util.TAG
import kotlinx.coroutines.launch
import javax.inject.Named

class RecipeListViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository,
    @Named(NAMED_TOKEN) private val token: String
) : ViewModel() {

    val recipes = mutableStateOf(emptyList<Recipe>())
    val query = mutableStateOf("")
    val selectedCategory = mutableStateOf<FoodCategory?>(null)
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)

    var categoryScrollPosition = 0f
    private var recipeListScrollPosition = 0

    init {
        onTriggerEvent(NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    NewSearchEvent -> {
                        newSearch()
                    }
                    NextPageEvent -> {
                        nextPage()
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "onTriggerEvent: $e cause: ${e.cause}")
            }
        }

    }

    private suspend fun newSearch() {
        loading.value = true
        resetSearchState()
        val result = repository.search(
            token = token,
            page = 1,
            query = query.value
        )

        recipes.value = result
        loading.value = false

    }

    private suspend fun nextPage() {
        if ((recipeListScrollPosition + 1) >= (page.value * API_PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            if (page.value > 1) {
                val result = repository.search(
                    token = token,
                    page = page.value,
                    query = query.value
                )
            }
            loading.value = false
        }

    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    fun onQueryChanged(newQuery: String) {
        query.value = newQuery
    }

    fun onSelectedCategoryChanged(newCategory: String) {
        selectedCategory.value = getFoodCategory(newCategory)
        onQueryChanged(newCategory)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }

    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.value += 1
    }

    private fun resetSearchState() {
        recipes.value = emptyList()
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    companion object {
        const val API_PAGE_SIZE = 30
    }
}
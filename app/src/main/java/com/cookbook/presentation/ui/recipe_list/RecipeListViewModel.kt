package com.cookbook.presentation.ui.recipe_list

import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookbook.domain.model.Recipe
import com.cookbook.repository.RecipeRepository
import com.cookbook.util.NAMED_TOKEN
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

    var categoryScrollPosition = 0f

    init {
        newSearch()
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

    fun newSearch() {
        viewModelScope.launch {
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
}
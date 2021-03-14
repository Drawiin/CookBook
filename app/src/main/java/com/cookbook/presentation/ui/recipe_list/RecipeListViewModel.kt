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

    init {
        newSearch()
    }

    fun setQuery(value: String) {
        query.value = value
    }

    fun newSearch() {
        viewModelScope.launch {
            val result = repository.search(
                token = token,
                page = 1,
                query = query.value
            )

            recipes.value = result
        }
    }

}
package com.cookbook.presentation.ui.recipe_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class RecipeListViewModel @ViewModelInject constructor(
    private val randomString: String
) : ViewModel() {
    var string: String = randomString

    init {
        println("VIEWMODEL: $randomString")
    }
}
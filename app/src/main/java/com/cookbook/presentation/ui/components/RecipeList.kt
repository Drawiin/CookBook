package com.cookbook.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cookbook.domain.model.Recipe
import com.cookbook.presentation.ui.recipe_list.RecipeListViewModel.Companion.API_PAGE_SIZE

@Composable
fun RecipeList(
    recipes: List<Recipe>,
    onChangeListScrollPosition: (Int) -> Unit,
    page: Int,
    loading: Boolean,
    onTriggerNextPageEvent: () -> Unit,
    onCardClicked: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (loading && recipes.isEmpty()) {
            LoadingRecipeListShimmer(imageHeight = 250.dp)
        } else {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                itemsIndexed(recipes) { index, recipe ->
                    onChangeListScrollPosition(index)
                    if ((index + 1) >= (page * API_PAGE_SIZE) && !loading) {
                        onTriggerNextPageEvent()
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            recipe.id?.let { id -> onCardClicked(id) }
                        }
                    )
                }
            }
        }
//        CircularIndeterminateProgressBar(
//            isDisplayed = loading
//        )
    }
}
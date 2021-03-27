package com.cookbook.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cookbook.presentation.ui.components.CircularIndeterminateProgressBar
import com.cookbook.presentation.ui.components.LoadingRecipeListShimmer
import com.cookbook.presentation.ui.components.RecipeCard
import com.cookbook.presentation.ui.components.SearchAppBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value
                val categoryScrollPosition = viewModel.categoryScrollPosition
                val loading = viewModel.loading.value

                Column(
                    modifier = Modifier.background(
                        color = Color.White
                    )
                ) {

                    SearchAppBar(
                        query = query,
                        selectedCategory = selectedCategory,
                        categoryScrollPosition = categoryScrollPosition,
                        onQueryChanged = viewModel::onQueryChanged,
                        newSearch = viewModel::newSearch,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition
                    )

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (!loading) {
                            LazyColumn(
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                itemsIndexed(recipes) { _, recipe ->
                                    RecipeCard(
                                        recipe = recipe,
                                        onClick = {}
                                    )
                                }
                            }
                        } else {
                            LoadingRecipeListShimmer(imageHeight = 250.dp)
                        }


                        CircularIndeterminateProgressBar(
                            isDisplayed = loading
                        )
                    }
                }
            }


        }
    }
}
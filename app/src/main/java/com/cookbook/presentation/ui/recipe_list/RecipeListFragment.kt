package com.cookbook.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cookbook.presentation.ui.components.AnimatedHeartButton
import com.cookbook.presentation.ui.components.CircularIndeterminateProgressBar
import com.cookbook.presentation.ui.components.HeartAnimationDefinition.HearthButtonState.ACTIVE
import com.cookbook.presentation.ui.components.HeartAnimationDefinition.HearthButtonState.IDLE
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

                Column {
                    SearchAppBar(
                        query = query,
                        selectedCategory = selectedCategory,
                        categoryScrollPosition = categoryScrollPosition,
                        onQueryChanged = viewModel::onQueryChanged,
                        newSearch = viewModel::newSearch,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition
                    )

                    val state = remember { mutableStateOf(IDLE) }

                    Row(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AnimatedHeartButton(
                            modifier = Modifier,
                            buttonState = state,
                            onToggle = {
                                state.value = when (state.value) {
                                    IDLE -> ACTIVE
                                    ACTIVE -> IDLE
                                }
                            }
                        )
                    }


                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyColumn {
                            itemsIndexed(recipes) { _, recipe ->
                                RecipeCard(
                                    recipe = recipe,
                                    onClick = {}
                                )
                            }
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
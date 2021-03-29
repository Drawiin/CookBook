package com.cookbook.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cookbook.presentation.BaseApplication
import com.cookbook.presentation.ui.components.CircularIndeterminateProgressBar
import com.cookbook.presentation.ui.components.LoadingRecipeListShimmer
import com.cookbook.presentation.ui.components.RecipeCard
import com.cookbook.presentation.ui.components.SearchAppBar
import com.cookbook.presentation.ui.recipe_list.RecipeListViewModel.Companion.API_PAGE_SIZE
import com.cookbook.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = application.isDark.value) {
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val categoryScrollPosition = viewModel.categoryScrollPosition
                    val loading = viewModel.loading.value
                    val page = viewModel.page.value

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                selectedCategory = selectedCategory,
                                categoryScrollPosition = categoryScrollPosition,
                                onQueryChanged = viewModel::onChangeQuery,
                                newSearch = {
                                    viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                },
                                onSelectedCategoryChanged = viewModel::onChangeSelectedCategory,
                                onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                onToggleTheme = application::toggleTheme
                            )
                        }
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
                                        viewModel.onChangeListScrollPosition(index)
                                        if ((index + 1) >= (page * API_PAGE_SIZE) && !loading) {
                                            viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)
                                        }
                                        RecipeCard(
                                            recipe = recipe,
                                            onClick = {}
                                        )
                                    }
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
}
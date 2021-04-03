package com.cookbook.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.cookbook.R
import com.cookbook.presentation.BaseApplication
import com.cookbook.presentation.ui.components.RecipeList
import com.cookbook.presentation.ui.components.SearchAppBar
import com.cookbook.presentation.ui.recipe.RecipeFragment
import com.cookbook.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
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
                                isDark = application.isDark.value,
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
                        RecipeList(
                            recipes = recipes,
                            onChangeListScrollPosition = viewModel::onChangeListScrollPosition,
                            page = page,
                            loading = loading,
                            onTriggerNextPageEvent = {
                                viewModel.onTriggerEvent(NextPageEvent)
                            },
                            onCardClicked = { id ->
                                findNavController()
                                    .navigate(
                                        R.id.action_recipeListFragment_to_recipeFragment,
                                        RecipeFragment.createBundle(id)
                                    )
                            }
                        )
                    }

                }
            }
        }
    }
}
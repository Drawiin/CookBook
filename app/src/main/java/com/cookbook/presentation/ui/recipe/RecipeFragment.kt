package com.cookbook.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cookbook.presentation.BaseApplication
import com.cookbook.presentation.ui.components.LoadingRecipeShimmer
import com.cookbook.presentation.ui.components.RecipeView
import com.cookbook.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private var recipeId: Int? = null
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeId = arguments?.getInt(RECIPE_ID_KEY)
        recipeId?.let { viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(id = it)) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value

                val recipe = viewModel.recipe.value


                AppTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = loading
                ) {
                    Scaffold() {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (loading && recipe == null)
                                LoadingRecipeShimmer(imageHeight = 250.dp)
                            else recipe?.let {
                                RecipeView(
                                    recipe = it,
                                )
                            }
                            Button(
                                shape = CircleShape,
                                onClick = { activity?.onBackPressed() },
                                modifier = Modifier
                                    .align(alignment = Alignment.TopStart)
                                    .size(48.dp)
                                    .padding(8.dp),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.surface
                                )
                            ) {
                                Icon(Icons.Default.ArrowBack)
                            }
                        }
                    }
                }

            }
        }
    }


    companion object {
        private const val RECIPE_ID_KEY = "RECIPE_ID_KEY"
        const val IMAGE_HEIGHT = 260
        fun createBundle(id: Int) = Bundle().apply {
            putInt(RECIPE_ID_KEY, id)
        }
    }
}
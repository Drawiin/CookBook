package com.cookbook.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.cookbook.domain.model.Recipe
import com.cookbook.presentation.ui.recipe.RecipeFragment.Companion.IMAGE_HEIGHT
import com.cookbook.util.DEFAULT_RECIPE_IMAGE
import com.cookbook.util.loadPicture

@Composable
fun RecipeView(
    recipe: Recipe,
) {
    ScrollableColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        recipe.featuredImage?.let { url ->
            val image = loadPicture(url, DEFAULT_RECIPE_IMAGE).value
            image?.asImageBitmap()?.let {
                Image(
                    bitmap = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(IMAGE_HEIGHT.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                recipe.title.let { title ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    ) {

                        Text(
                            text = title.toString(),
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h3
                        )

                        val rank = recipe.rating.toString()
                        Text(
                            text = rank,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
                recipe.publisher.let { publisher ->
                    val updated = recipe.dateUpdated
                    Text(
                        text = if (updated != null) {
                            "Updated ${updated} by ${publisher}"
                        } else {
                            "By ${publisher}"
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        style = MaterialTheme.typography.caption
                    )
                }
                for (ingredient in recipe.ingredients) {
                    Text(
                        text = ingredient,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
                recipe.cookingInstructions.let { instructions ->
                    Text(
                        text = instructions.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}
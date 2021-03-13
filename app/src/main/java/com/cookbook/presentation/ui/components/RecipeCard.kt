package com.cookbook.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.cookbook.R
import com.cookbook.domain.model.Recipe

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(bottom = 6.dp, top = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,

        ) {
        Column {
            recipe.featuredImage?.let { url ->
                Image(
                    // FIXME -> Load Resource Asynchronously
                    bitmap = imageResource(id = R.drawable.empty_plate),
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(225.dp),
                    contentScale = ContentScale.Crop
                )
            }
            recipe.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.fillMaxWidth(0.80f)
                    )
                    Text(
                        text = recipe.rating.toString(),
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }
}
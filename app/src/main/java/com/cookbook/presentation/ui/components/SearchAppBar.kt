package com.cookbook.presentation.ui.components

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cookbook.presentation.ui.recipe_list.FoodCategory
import com.cookbook.presentation.ui.recipe_list.getAllFoodCategories

@Composable
fun SearchAppBar(
    query: String,
    selectedCategory: FoodCategory?,
    categoryScrollPosition: Float,
    onQueryChanged: (String) -> Unit,
    newSearch: () -> Unit,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryScrollPosition: (Float) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        color = Color.White
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = query,
                    onValueChange = onQueryChanged,
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search)
                    },
                    onImeActionPerformed = { action, keyboardController ->
                        if (action == ImeAction.Search) {
                            newSearch()
                            keyboardController?.hideSoftwareKeyboard()
                        }
                    },
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface
                    ),
                    backgroundColor = MaterialTheme.colors.surface
                )
            }

            val scrollState = rememberScrollState()
            ScrollableRow(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                scrollState = scrollState
            ) {
                scrollState.scrollTo(categoryScrollPosition)
                for (category in getAllFoodCategories()) {
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onExecuteSearch = newSearch,
                        onSelectedCategoryChange = {
                            onSelectedCategoryChanged(it)
                            onChangeCategoryScrollPosition(scrollState.value)
                        }
                    )
                }
            }
        }
    }
}
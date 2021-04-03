package com.cookbook.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material.AmbientContentColor
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource

@Composable
fun MyAppIcon(
    @DrawableRes
    resourceId: Int,
    modifier: Modifier = Modifier,
    tint: Color = AmbientContentColor.current
) {
    Icon(
        imageVector = vectorResource(id = resourceId),
        modifier = modifier,
        tint = tint
    )
}
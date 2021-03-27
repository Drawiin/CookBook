 package com.cookbook.presentation.ui.components

import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.TransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.cookbook.R
import com.cookbook.presentation.ui.components.HeartAnimationDefinition.HearthButtonState.ACTIVE
import com.cookbook.presentation.ui.components.HeartAnimationDefinition.HearthButtonState.IDLE
import com.cookbook.presentation.ui.components.HeartAnimationDefinition.heartSize
import com.cookbook.presentation.ui.components.HeartAnimationDefinition.heartTransitionDefinition
import com.cookbook.util.loadPicture

@Composable
fun AnimatedHeartButton(
    modifier: Modifier,
    buttonState: MutableState<HeartAnimationDefinition.HearthButtonState>,
    onToggle: () -> Unit
) {
    val toState = when (buttonState.value) {
        IDLE -> ACTIVE
        ACTIVE -> IDLE
    }

    val state = transition(
        definition = heartTransitionDefinition,
        initState = buttonState.value,
        toState = toState
    )

    HeartButton(
        modifier = modifier,
        buttonState = buttonState,
        state = state,
        onToggle = onToggle
    )
}

@Composable
private fun HeartButton(
    modifier: Modifier,
    buttonState: MutableState<HeartAnimationDefinition.HearthButtonState>,
    state: TransitionState,
    onToggle: () -> Unit,
) {
    when (buttonState.value) {
        IDLE -> {
            loadPicture(R.drawable.heart_grey).value?.let { image ->
                Image(
                    bitmap = image.asImageBitmap(),
                    modifier = Modifier
                        .clickable(onClick = onToggle, indication = null)
                        .width(state[heartSize])
                        .height(state[heartSize])
                )
            }
        }
        ACTIVE -> {
            loadPicture(R.drawable.heart_red).value?.let { image ->
                Image(
                    bitmap = image.asImageBitmap(),
                    modifier = Modifier
                        .clickable(onClick = onToggle, indication = null)
                        .width(state[heartSize])
                        .height(state[heartSize])
                )
            }
        }
    }
}

object HeartAnimationDefinition {
    enum class HearthButtonState {
        IDLE, ACTIVE
    }

    val heartColor = ColorPropKey(label = "heartColor")
    val heartSize = DpPropKey(label = "heartSize")

    private val idleIconSize = 50.dp
    private val expandedIconSize = 80.dp

    val heartTransitionDefinition = transitionDefinition<HearthButtonState> {
        state(IDLE) {
            this[heartColor] = Color.LightGray
            this[heartSize] = idleIconSize
        }

        state(ACTIVE) {
            this[heartColor] = Color.Red
            this[heartSize] = idleIconSize

            transition(
                IDLE to ACTIVE
            ) {
                heartColor using tween(durationMillis = 500)

                heartSize using keyframes {
                    durationMillis = 500
                    expandedIconSize at 100
                    idleIconSize at 200
                }
            }

            transition(
                ACTIVE to IDLE
            ) {
                heartColor using tween(durationMillis = 500)

                heartSize using keyframes {
                    durationMillis = 500
                    expandedIconSize at 100
                    idleIconSize at 200
                }
            }
        }
    }
}
package com.cookbook.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.cookbook.presentation.ui.components.PulseAnimationDefinitions.PulseState.FINAL
import com.cookbook.presentation.ui.components.PulseAnimationDefinitions.PulseState.INITIAL
import com.cookbook.presentation.ui.components.PulseAnimationDefinitions.pulseDefinition
import com.cookbook.presentation.ui.components.PulseAnimationDefinitions.pulsePropKey

@Composable
fun PulsingDemo() {
    val color = MaterialTheme.colors.primary

    val pulseAnim = transition(
        definition = pulseDefinition,
        initState = INITIAL,
        toState = FINAL
    )

    val pulseMagnitude = pulseAnim[pulsePropKey]

    Canvas(
        modifier = Modifier.fillMaxWidth().height(55.dp),
    ) {
        drawCircle(
            radius = pulseMagnitude,
            brush = SolidColor(color)
        )
    }
}

object PulseAnimationDefinitions {
    enum class PulseState {
        INITIAL, FINAL
    }

    val pulsePropKey = FloatPropKey("pulseKey")

    val pulseDefinition = transitionDefinition<PulseState> {
        state(INITIAL) { this[pulsePropKey] = 40f }
        state(FINAL) { this[pulsePropKey] = 50f }

        transition(
            INITIAL to FINAL
        ) {
            pulsePropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        }
    }
}
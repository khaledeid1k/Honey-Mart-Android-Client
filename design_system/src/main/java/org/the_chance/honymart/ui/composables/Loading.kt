package org.the_chance.honymart.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun Loading(
    state: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = MaterialTheme.dimens.lottieLoading,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading)
    )
    AnimatedVisibility(
        visible = state,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        Box(
            modifier = modifier
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { })
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                modifier = Modifier.size(size),
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
        }
    }
}
package com.jop.cointracker.util

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp

fun Modifier.shimmerBackground(shape: Shape = RoundedCornerShape(0.dp)): Modifier = composed {
    val transition = rememberInfiniteTransition()

    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        ),
    )

    val shimmerColors = listOf(
        Color.Transparent,
        Color.White.copy(alpha = 0.25f),
        Color.Transparent,
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(translateAnimation + 85f, translateAnimation + 85f),
        tileMode = TileMode.Mirror,
    )

    Modifier.drawWithContent {
        drawContent()
        drawRect(brush = brush)
    }
//    return@composed this.then(this.background(brush, shape))
}
//
//fun Modifier.shimmerEffect(
//    progress: Float,
//    shimmerSize: Dp = 152.dp,
//    shimmerColor: Color = Color.White.copy(alpha = 0.25f),
//) = this.composed {
//    val shimmerSizePx = shimmerSize.toPx()
//    Modifier.drawWithContent {
//        drawContent()
//        val adjustedWidth = size.width + shimmerSizePx * 2
//        val x = adjustedWidth * progress - shimmerSizePx
//        drawRect(
//            brush = Brush.horizontalGradient(
//                colors = listOf(
//                    Color.Transparent,
//                    shimmerColor,
//                    Color.Transparent,
//                ),
//                startX = x,
//                endX = x + shimmerSizePx,
//            ),
//            topLeft = Offset(x = x, y = 0f),
//            size = Size(
//                width = shimmerSizePx,
//                height = size.height,
//            ),
//        )
//    }
//}
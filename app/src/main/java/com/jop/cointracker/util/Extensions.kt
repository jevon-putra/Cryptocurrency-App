package com.jop.cointracker.util

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import java.text.NumberFormat
import java.util.Locale

fun Modifier.shimmerBackground(isLoading: Boolean = true): Modifier = composed {
    var shimmer: Brush? = null
    val shimmerColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.surface

    if(isLoading){
        val transition = rememberInfiniteTransition(label = "shimmer")

        val translateAnimation by transition.animateFloat(
            initialValue = 0f,
            targetValue = 400f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
                RepeatMode.Restart
            ),
            label = "shimmer",
        )

        val shimmerColors = listOf(
            Color.Transparent,
            shimmerColor.copy(alpha = 0.5f),
            Color.Transparent,
        )

        shimmer = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(translateAnimation, translateAnimation),
            end = Offset(translateAnimation + 85f, translateAnimation + 85f),
            tileMode = TileMode.Mirror,
        )
    }

    Modifier.drawWithContent {
        drawContent()
        if(shimmer != null){
            drawRect(color = backgroundColor)
            drawRect(brush = shimmer)
        }
    }
}

fun Double.formatSupplyCrypto(): String{
    val suffixes = listOf("", "", "million", "billion", "trillion")
    var index = 0
    var value = this

    while (value >= 1000) {
        value /= 1000
        index++
    }

    val formattedNumber = String.format("%.2f", value)
    return if(this > 0) "$formattedNumber ${suffixes[index]}" else "--"
}

fun Double.formatNumberShort(): String{
    val suffixes = listOf("", "K", "M", "B", "T")
    var index = 0
    var value = this

    while (value >= 1000) {
        value /= 1000
        index++
    }

    val formattedNumber = String.format("%.2f", value)
    return "$formattedNumber${suffixes[index]}"
}

fun Double.formatCurrencyNumber(): String{
    val formatedNumber = String.format("%.3f", this)
    val formatCurrency = NumberFormat.getCurrencyInstance(Locale("en", "US"))

    if(formatedNumber.endsWith(".000") && this < 1){
        formatCurrency.minimumFractionDigits = 7
    }

    return formatCurrency.format(this)
}


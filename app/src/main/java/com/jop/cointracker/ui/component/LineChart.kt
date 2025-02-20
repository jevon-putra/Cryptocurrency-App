package com.jop.cointracker.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LineChart(modifier: Modifier = Modifier, list: List<Double> = listOf(), isShowDifference: Boolean = false){
    val zipList: List<Pair<Double, Double>> = list.zipWithNext()
    val first = if(list.isNotEmpty()) list.first() else 0.0
    val last = if(list.isNotEmpty()) list.last() else 0.0
    val max = if(list.isNotEmpty()) list.max() else 0.0
    val min = if(list.isNotEmpty()) list.min() else 0.0
    val colorUpTrend = MaterialTheme.colorScheme.primary
    val colorDownTrend = MaterialTheme.colorScheme.error
    val colorDivider = MaterialTheme.colorScheme.outline

    Box(
        modifier = modifier
    ) {
        if(isShowDifference){
            Canvas(modifier = Modifier.fillMaxSize()) {
                val firstValuePercentage = getValuePercentageForRange(first, max, min)
                val getFirstHeightValue = size.height.times(1 - firstValuePercentage).toFloat()

                drawLine(
                    color = colorDivider,
                    strokeWidth = 1f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                    start = Offset(x = 0f, y = getFirstHeightValue),
                    end = Offset(x = size.width, y = getFirstHeightValue),
                )
            }
        }

        Row(modifier = modifier.fillMaxWidth()) {
            for (pair in zipList) {
                val color = if((!isShowDifference && last > first) || (isShowDifference && pair.second > first)) colorUpTrend else colorDownTrend
                val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
                val toValuePercentage = getValuePercentageForRange(pair.second, max, min)

                Canvas(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    onDraw = {
                        val spacing = size.width / list.size
                        val fromPoint = Offset(x = 0f, y = size.height.times(1 - fromValuePercentage).toFloat())
                        val toPoint = Offset(x = size.width, y = size.height.times(1 - toValuePercentage).toFloat())

                        val strokePath = Path().apply {
                            moveTo(fromPoint.x , fromPoint.y)
                            cubicTo(fromPoint.x + spacing, fromPoint.y, toPoint.x - spacing, toPoint.y, toPoint.x, toPoint.y)
                        }

                        drawPath(
                            path = strokePath,
                            color = color,
                            style = Stroke(
                                width = 1.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )

                        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
                            .asComposePath()
                            .apply {
                                lineTo(size.width, size.height)
                                lineTo(0f, size.height)
                                close()
                            }
                        drawPath(
                            fillPath,
                            brush = Brush.verticalGradient(
                                listOf(
                                    color,
                                    Color.Transparent,
                                ),
                            ),
                        )
                    }
                )
            }
        }
    }
}

private fun getValuePercentageForRange(value: Double, max: Double, min: Double) = (value - min) / (max - min)

@Preview
@Composable
fun PreviewLineChart(){
    Box {
        LineChart(isShowDifference = true)
    }
}
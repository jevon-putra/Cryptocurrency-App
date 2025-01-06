package com.jop.cointracker.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jop.cointracker.ui.theme.errorLight
import com.jop.cointracker.ui.theme.primaryDark

@Composable
fun LineChart(modifier: Modifier = Modifier, list: List<Double> = listOf()){
    val zipList: List<Pair<Double, Double>> = list.zipWithNext()
    val avg = list.average()
    val max = list.max()
    val min = list.min()
    val spacing = 5f
    val upTrend = list.last() >= avg

    Row(
        modifier = modifier
    ) {
        for (pair in zipList) {
            val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
            val toValuePercentage = getValuePercentageForRange(pair.second, max, min)
            Canvas(
                modifier = Modifier.fillMaxHeight().weight(1f),
                onDraw = {
                    val fromPoint = Offset(x = 0f, y = size.height.times(1 - fromValuePercentage).toFloat())
                    val toPoint = Offset(x = size.width, y = size.height.times(1 - toValuePercentage).toFloat())

                    val strokePath = Path().apply {
                        moveTo(fromPoint.x , fromPoint.y)
                        cubicTo(fromPoint.x + spacing, fromPoint.y, toPoint.x - spacing, toPoint.y, toPoint.x, toPoint.y)
                    }

                    drawPath(
                        path = strokePath,
                        color = if(upTrend) primaryDark else errorLight,
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
                                if(upTrend) primaryDark else errorLight,
                                Color.Transparent,
                            ),
                            endY = size.height
                        ),
                    )
                }
            )
        }
    }
}

private fun getValuePercentageForRange(value: Double, max: Double, min: Double) =
    (value - min) / (max - min)

@Preview
@Composable
fun PreviewLineChart(){
    Box {
        LineChart()
    }
}
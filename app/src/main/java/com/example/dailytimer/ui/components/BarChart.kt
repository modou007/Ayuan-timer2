package com.example.dailytimer.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailytimer.ui.theme.*

@Composable
fun BarChart(
    columns: List<BarColumn>,
    yLabels: List<String>,
    xLabels: List<String>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(240.dp)
) {
    val density = LocalDensity.current
    val textColor = TextGray
    val textSizeSp = 11

    Column(modifier = modifier) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
            val paddingStart = 40.dp.toPx()
            val paddingEnd = 16.dp.toPx()
            val paddingTop = 20.dp.toPx()
            val paddingBottom = 24.dp.toPx()
            val chartWidth = size.width - paddingStart - paddingEnd
            val chartHeight = size.height - paddingTop - paddingBottom

            // Grid lines
            yLabels.reversed().forEachIndexed { index, _ ->
                val y = paddingTop + (chartHeight / (yLabels.size - 1)) * index
                drawLine(
                    color = ProgressBg,
                    start = Offset(paddingStart, y),
                    end = Offset(size.width - paddingEnd, y),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Bars
            val barWidth = 36.dp.toPx()
            val colSpacing = chartWidth / columns.size
            columns.forEachIndexed { colIndex, col ->
                val colCenterX = paddingStart + colSpacing * colIndex + colSpacing / 2
                val barLeft = colCenterX - barWidth / 2

                val maxVal = yLabels.firstOrNull()?.replace("h", "")?.toFloatOrNull() ?: 20f
                var currentY = paddingTop + chartHeight

                col.segments.forEachIndexed { segIndex, seg ->
                    val barHeight = (seg.value / maxVal) * chartHeight
                    val topY = currentY - barHeight
                    val isBottom = segIndex == 0
                    val isTop = segIndex == col.segments.size - 1
                    val cornerRadius = if (isBottom || isTop) 12.dp.toPx() else 0f

                    drawRoundRect(
                        color = seg.color,
                        topLeft = Offset(barLeft, topY),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                    currentY -= barHeight
                }
            }
        }
        // X-axis labels
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            xLabels.forEach { label ->
                Text(
                    text = label,
                    style = Typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = textSizeSp.sp),
                    color = textColor
                )
            }
        }
    }
}

data class BarColumn(val segments: List<BarSegment>)
data class BarSegment(val value: Float, val color: Color)

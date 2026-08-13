package com.example.dailytimer.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.dailytimer.ui.theme.*
import kotlin.math.min

@Composable
fun RingChart(
    segments: List<RingSegment>,
    modifier: Modifier = Modifier.size(200.dp)
) {
    val total = segments.fold(0f) { acc, seg -> acc + seg.percent }.coerceAtLeast(0.001f)
    Canvas(modifier = modifier) {
        val diameter = min(size.width, size.height)
        val radius = diameter / 2f
        val strokeWidth = 24.dp.toPx()
        val center = Offset(size.width / 2, size.height / 2)
        val arcSize = Size(diameter - strokeWidth, diameter - strokeWidth)
        val topLeft = Offset(center.x - (diameter - strokeWidth) / 2, center.y - (diameter - strokeWidth) / 2)

        // Background ring
        drawArc(
            color = ProgressBg,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )

        var currentAngle = -90f
        segments.forEach { seg ->
            val sweep = (seg.percent / total) * 360f
            drawArc(
                color = seg.color,
                startAngle = currentAngle,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )
            currentAngle += sweep
        }
    }
}

data class RingSegment(val percent: Float, val color: Color)

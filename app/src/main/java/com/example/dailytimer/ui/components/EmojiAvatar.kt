package com.example.dailytimer.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.dailytimer.ui.theme.*

@Composable
fun EmojiAvatar(
    iconType: String,
    iconColor: String,
    modifier: Modifier = Modifier.size(48.dp)
) {
    val bgColor = Accent
    val color = when (iconColor) {
        "red" -> Red
        "yellow" -> Yellow
        "green" -> Green
        "blue" -> Blue
        "purple" -> Purple
        else -> Red
    }
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(bgColor),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        when (iconType) {
            "ball" -> BallIcon(color, Modifier.size(40.dp))
            "face" -> FaceIcon(Modifier.size(28.dp))
            "cat" -> CatIcon(Modifier.size(37.dp))
            else -> BallIcon(color, Modifier.size(40.dp))
        }
    }
}

@Composable
private fun BallIcon(color: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = androidx.compose.ui.Alignment.Center) {
        Box(modifier = Modifier.fillMaxSize().clip(CircleShape).background(color))
        // Highlight
        Box(
            modifier = Modifier
                .offset(x = 10.dp, y = 10.dp)
                .size(8.57.dp)
                .clip(CircleShape)
                .background(Color(0xFFFF6B7A))
        )
    }
}

@Composable
private fun FaceIcon(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = androidx.compose.ui.Alignment.Center) {
        Box(modifier = Modifier.fillMaxSize().clip(CircleShape).background(Yellow))
        // Eyes (closed lines)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 2.dp.toPx()
            drawLine(
                color = TextDark, start = Offset(9.dp.toPx(), 11.dp.toPx()),
                end = Offset(13.dp.toPx(), 11.dp.toPx()), strokeWidth = strokeWidth, cap = StrokeCap.Round
            )
            drawLine(
                color = TextDark, start = Offset(17.dp.toPx(), 11.dp.toPx()),
                end = Offset(21.dp.toPx(), 11.dp.toPx()), strokeWidth = strokeWidth, cap = StrokeCap.Round
            )
            // Mouth
            drawLine(
                color = TextDark, start = Offset(10.dp.toPx(), 17.dp.toPx()),
                end = Offset(18.dp.toPx(), 17.dp.toPx()), strokeWidth = strokeWidth, cap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun CatIcon(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = androidx.compose.ui.Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = size.width / 2
            val cy = size.height / 2
            // Ears (triangles approximated as small rects with offset)
            drawCircle(color = Orange, radius = size.minDimension / 2, center = Offset(cx, cy + 2.dp.toPx()))
            // Eyes
            drawCircle(color = TextDark, radius = 3.96.dp.toPx(), center = Offset(cx - 6.dp.toPx(), cy - 2.dp.toPx()))
            drawCircle(color = TextDark, radius = 3.96.dp.toPx(), center = Offset(cx + 6.dp.toPx(), cy - 2.dp.toPx()))
            // Mouth line
            drawLine(
                color = TextDark, start = Offset(cx - 3.dp.toPx(), cy + 4.dp.toPx()),
                end = Offset(cx + 3.dp.toPx(), cy + 4.dp.toPx()), strokeWidth = 1.dp.toPx(), cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun DottedDivider(modifier: Modifier = Modifier.fillMaxWidth()) {
    Canvas(modifier = modifier.height(2.dp)) {
        drawLine(
            color = Divider,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(4.dp.toPx(), 4.dp.toPx()), 0f),
            cap = StrokeCap.Round
        )
    }
}

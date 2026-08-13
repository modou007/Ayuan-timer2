package com.example.dailytimer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailytimer.ui.theme.*

@Composable
fun HomeToolbar(
    onResetToday: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Accent)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "每日计时器",
            style = Typography.titleLarge,
            color = TextDark
        )
        ResetTodayButton(onClick = onResetToday)
    }
}

@Composable
fun ScreenToolbar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Accent)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .size(32.dp)
                .background(CardBg, CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = TextDark,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            text = title,
            style = Typography.titleLarge,
            color = TextDark,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(32.dp))
    }
}

@Composable
private fun ResetTodayButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(36.dp)
            .background(CardBg, CircleShape)
            .shadow(2.dp, CircleShape, spotColor = Color.Black.copy(alpha = 0.08f))
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Reset icon (circle arrow)
        Box(modifier = Modifier.size(14.dp)) {
            // simplified icon
        }
        Text(
            text = "今日清零",
            style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = StartBtn,
            fontSize = 12.sp
        )
    }
}

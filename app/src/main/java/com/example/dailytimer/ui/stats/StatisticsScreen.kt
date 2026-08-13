package com.example.dailytimer.ui.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailytimer.ui.components.*
import com.example.dailytimer.ui.theme.*

@Composable
fun StatisticsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        StatusBar()
        ScreenToolbar(title = "今日统计", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatsCard()
        }
    }
}

@Composable
private fun StatsCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp), spotColor = Color.Black.copy(alpha = 0.08f))
            .background(CardBg, RoundedCornerShape(20.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "总计时 15h 47m",
            style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
            color = TextGray
        )
        Box(contentAlignment = Alignment.Center) {
            RingChart(
                segments = listOf(
                    RingSegment(35f, Red),
                    RingSegment(25f, Yellow),
                    RingSegment(15f, Orange)
                )
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "100%",
                    style = Typography.titleLarge.copy(fontSize = 18.sp),
                    color = TextDark
                )
                Text(
                    text = "完成度",
                    style = Typography.bodySmall.copy(fontSize = 11.sp),
                    color = TextGray
                )
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            LegendItem("午睡计时", "5h 12m", "35%", Red)
            DottedDivider()
            LegendItem("夜间睡眠", "8h 30m", "25%", Yellow)
            DottedDivider()
            LegendItem("喂奶间隔", "2h 05m", "15%", Orange)
        }
    }
}

@Composable
private fun LegendItem(name: String, time: String, percent: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier.size(12.dp).background(color, CircleShape))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
                color = TextDark
            )
            Text(
                text = time,
                style = Typography.bodySmall.copy(fontSize = 12.sp),
                color = TextGray
            )
        }
        Text(
            text = percent,
            style = Typography.labelMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
            color = color
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            tint = TextGray,
            modifier = Modifier.size(20.dp)
        )
    }
}

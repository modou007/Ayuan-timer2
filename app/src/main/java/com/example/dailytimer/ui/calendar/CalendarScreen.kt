package com.example.dailytimer.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
fun CalendarScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(1) } // 0=week, 1=month, 2=custom
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        StatusBar()
        ScreenToolbar(title = "日历趋势", onBack = onBack)

        // Toggle Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TogglePill("周", selectedTab == 0, { selectedTab = 0 }, Modifier.weight(1f))
            TogglePill("月", selectedTab == 1, { selectedTab = 1 }, Modifier.weight(1f))
            TogglePill("自定义", selectedTab == 2, { selectedTab = 2 }, Modifier.weight(1f))
        }

        // Chart Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(20.dp), spotColor = Color.Black.copy(alpha = 0.08f))
                .background(CardBg, RoundedCornerShape(20.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "本周各计时器累计",
                    style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
                    color = TextDark
                )
                Text(
                    text = "47h",
                    style = Typography.labelMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                    color = TextDark
                )
            }
            BarChart(
                columns = listOf(
                    BarColumn(listOf(
                        BarSegment(12f, Red), BarSegment(5f, Yellow), BarSegment(2f, Orange), BarSegment(1f, Green)
                    )),
                    BarColumn(listOf(
                        BarSegment(14f, Red), BarSegment(5f, Yellow), BarSegment(2f, Orange)
                    )),
                    BarColumn(listOf(
                        BarSegment(15f, Red), BarSegment(5f, Yellow), BarSegment(2f, Orange)
                    )),
                    BarColumn(listOf(
                        BarSegment(13f, Red), BarSegment(5f, Yellow), BarSegment(2f, Orange)
                    ))
                ),
                yLabels = listOf("20h", "15h", "10h", "5h"),
                xLabels = listOf("周一", "周二", "周三", "周四")
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChartLegendDot("午睡", Red)
                ChartLegendDot("夜间", Yellow)
                ChartLegendDot("喂奶", Orange)
            }
        }

        // Detail Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .shadow(8.dp, RoundedCornerShape(20.dp), spotColor = Color.Black.copy(alpha = 0.08f))
                .background(CardBg, RoundedCornerShape(20.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "周三 · 13h 25m",
                style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
                color = TextDark
            )
            DetailRow("午睡计时", "5h 30m", Red)
            DottedDivider()
            DetailRow("夜间睡眠", "5h 55m", Yellow)
            DottedDivider()
            DetailRow("喂奶间隔", "2h 00m", Orange)
        }
    }
}

@Composable
private fun TogglePill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .shadow(4.dp, RoundedCornerShape(20.dp), spotColor = Color.Black.copy(alpha = 0.04f))
            .background(if (selected) Accent else CardBg, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold,
                fontSize = 14.sp
            ),
            color = if (selected) TextDark else ToggleInactive
        )
    }
}

@Composable
private fun ChartLegendDot(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(modifier = Modifier.size(10.dp).background(color, CircleShape))
        Text(
            text = label,
            style = Typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 11.sp),
            color = TextGray
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.size(10.dp).background(color, CircleShape))
        Text(
            text = label,
            style = Typography.bodySmall.copy(fontSize = 12.sp),
            color = TextGray,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = Typography.labelMedium.copy(fontSize = 12.sp),
            color = TextDark
        )
    }
}

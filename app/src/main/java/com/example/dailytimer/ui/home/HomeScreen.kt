package com.example.dailytimer.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
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
import com.example.dailytimer.data.entity.TimerEntity
import com.example.dailytimer.viewmodel.TimerState
import com.example.dailytimer.viewmodel.TimerStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    timers: List<TimerState>,
    onAddTimer: () -> Unit,
    onResetToday: () -> Unit,
    onStart: (Long) -> Unit,
    onPause: (Long) -> Unit,
    onStop: (Long) -> Unit,
    onDelete: (TimerState) -> Unit,
    onNavigateStats: () -> Unit,
    onNavigateCalendar: () -> Unit,
    onNavigateStopwatch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize().background(BgPrimary)) {
        Column(modifier = Modifier.fillMaxSize()) {
            StatusBar()
            HomeToolbar(onResetToday = onResetToday)
            // Quick navigation row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                NavChip("📊 统计", onNavigateStats, Modifier.weight(1f))
                NavChip("📅 日历", onNavigateCalendar, Modifier.weight(1f))
                NavChip("⏱ 秒表", onNavigateStopwatch, Modifier.weight(1f))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                timers.forEachIndexed { index, timer ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { value ->
                            if (value == SwipeToDismissBoxValue.EndToStart) {
                                onDelete(timer)
                            }
                            false // Don't dismiss visually; deletion happens after dialog confirm
                        }
                    )
                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(StartBtn, RoundedCornerShape(20.dp))
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = TextWhite,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        enableDismissFromStartToEnd = false
                    ) {
                        TimerCard(
                            timer = timer,
                            onStart = { onStart(timer.id) },
                            onPause = { onPause(timer.id) },
                            onStop = { onStop(timer.id) }
                        )
                    }
                    if (index < timers.size - 1) {
                        DottedDivider()
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = onAddTimer,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(56.dp)
                .shadow(12.dp, CircleShape, spotColor = ShadowBrown),
            containerColor = Accent,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = TextDark,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun TimerCard(
    timer: TimerState,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isRunning = timer.status == TimerStatus.RUNNING
    val isPaused = timer.status == TimerStatus.PAUSED
    val isIdle = timer.status == TimerStatus.IDLE

    val cardColor = CardBg
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp), spotColor = Color.Black.copy(alpha = 0.08f), ambientColor = Color.Black.copy(alpha = 0.08f))
            .background(cardColor, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Top row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            EmojiAvatar(iconType = timer.iconType, iconColor = timer.iconColor)
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = timer.name,
                    style = Typography.titleLarge.copy(fontSize = 18.sp),
                    color = TextDark
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "今日累计",
                        style = Typography.bodyLarge.copy(fontSize = 14.sp),
                        color = TextGray
                    )
                    Text(
                        text = timer.dailyTotal,
                        style = Typography.labelMedium.copy(fontSize = 14.sp),
                        color = TextGray
                    )
                }
            }
        }

        // Session timer
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = "本次计时",
                style = Typography.bodySmall.copy(fontSize = 12.sp),
                color = TextGray
            )
            Text(
                text = timer.sessionTime,
                style = Typography.headlineLarge.copy(
                    fontSize = 32.sp,
                    color = if (isIdle) Color(0xFFCCCCCC) else TextDark
                )
            )
        }

        // Progress
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(ProgressBg, RoundedCornerShape(2.dp))
        ) {
            if (timer.progress > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(timer.progress.coerceIn(0f, 1f))
                        .background(timer.color, RoundedCornerShape(2.dp))
                )
            }
        }

        // Buttons
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TimerActionButton(
                text = "开始",
                icon = "▶",
                bgColor = StartBtn,
                textColor = TextWhite,
                enabled = !isRunning,
                onClick = onStart,
                modifier = Modifier.weight(1f)
            )
            TimerActionButton(
                text = "暂停",
                icon = "⏸",
                bgColor = if (isRunning) PauseBtn else DisabledBg,
                textColor = if (isRunning) TextWhite else DisabledText,
                enabled = isRunning,
                onClick = onPause,
                modifier = Modifier.weight(1f)
            )
            TimerActionButton(
                text = "停止保存",
                icon = "⏹",
                bgColor = if (isRunning || isPaused) StopBtn else DisabledBg,
                textColor = if (isRunning || isPaused) TextWhite else DisabledText,
                enabled = isRunning || isPaused,
                onClick = onStop,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NavChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .background(CardBg, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge.copy(fontWeight = FontWeight.Medium, fontSize = 13.sp),
            color = TextDark
        )
    }
}

@Composable
private fun TimerActionButton(
    text: String,
    icon: String,
    bgColor: Color,
    textColor: Color,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            disabledContainerColor = bgColor
        ),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = icon, fontSize = 12.sp, color = textColor)
            Text(
                text = text,
                style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                color = textColor
            )
        }
    }
}

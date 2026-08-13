package com.example.dailytimer.ui.stopwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.dailytimer.viewmodel.StopwatchViewModel

@Composable
fun StopwatchScreen(
    viewModel: StopwatchViewModel,
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
        ScreenToolbar(title = "秒表", onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = viewModel.timeText.value,
                style = Typography.displayLarge.copy(fontSize = 56.sp),
                color = TextDark
            )
            Text(
                text = viewModel.msText.value,
                style = Typography.bodyMedium.copy(fontSize = 18.sp),
                color = TextGray
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleButton(text = "计次", onClick = { viewModel.lap() }, size = 80.dp, strokeColor = Divider)
            CircleButton(
                text = if (viewModel.isRunning.value) "暂停" else "开始",
                onClick = { if (viewModel.isRunning.value) viewModel.pause() else viewModel.start() },
                size = 100.dp,
                bgColor = if (viewModel.isRunning.value) PauseBtn else StartBtn,
                textColor = TextWhite,
                shadow = 16.dp
            )
            CircleButton(text = "重置", onClick = { viewModel.reset() }, size = 80.dp, strokeColor = Divider)
        }

        // Lap list
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "计次记录",
                    style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
                    color = TextDark
                )
                Text(
                    text = "清空",
                    style = Typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 12.sp),
                    color = StartBtn,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .clickable { viewModel.clearLaps() }
                        .padding(4.dp)
                )
            }
            DottedDivider()
            viewModel.laps.forEach { (num, time) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = num,
                        style = Typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 13.sp),
                        color = TextGray
                    )
                    Text(
                        text = time,
                        style = Typography.labelMedium.copy(fontSize = 13.sp),
                        color = TextDark
                    )
                }
                DottedDivider()
            }
        }
    }
}

@Composable
private fun CircleButton(
    text: String,
    onClick: () -> Unit,
    size: androidx.compose.ui.unit.Dp,
    bgColor: Color = CardBg,
    textColor: Color = TextGray,
    strokeColor: Color = Color.Transparent,
    shadow: androidx.compose.ui.unit.Dp = 8.dp
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(size)
            .shadow(shadow, CircleShape, spotColor = Color.Black.copy(alpha = 0.08f)),
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        shape = CircleShape,
        border = if (strokeColor != Color.Transparent) androidx.compose.foundation.BorderStroke(1.dp, strokeColor) else null,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
            color = textColor
        )
    }
}

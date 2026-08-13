package com.example.dailytimer.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailytimer.ui.components.EmojiAvatar
import com.example.dailytimer.ui.theme.*
import com.example.dailytimer.viewmodel.TimerState

@Composable
fun DeleteConfirmDialog(
    timer: TimerState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OverlayDark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .shadow(24.dp, RoundedCornerShape(20.dp), spotColor = Color.Black.copy(alpha = 0.2f))
                .background(CardBg, RoundedCornerShape(20.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Warning icon
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(WarningBg, CircleShape)
                    .border(2.dp, WarningRed, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = WarningRed
                )
            }
            Text(
                text = "确认删除？",
                style = Typography.titleLarge.copy(fontSize = 18.sp),
                color = TextDark
            )
            Text(
                text = "删除计时器将清除所有历史数据，且无法恢复",
                style = Typography.bodyLarge.copy(fontSize = 14.sp),
                color = TextGray,
                textAlign = TextAlign.Center
            )
            // Timer info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DetailBg, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EmojiAvatar(iconType = timer.iconType, iconColor = timer.iconColor, modifier = Modifier.size(36.dp))
                Text(
                    text = timer.name,
                    style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
                    color = TextDark
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CancelBtnBg),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "取消",
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 15.sp),
                        color = TextDark
                    )
                }
                Button(
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f).height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StartBtn),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "确认删除",
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        color = TextWhite
                    )
                }
            }
        }
    }
}

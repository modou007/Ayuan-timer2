package com.example.dailytimer.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailytimer.ui.components.EmojiAvatar
import com.example.dailytimer.ui.theme.*
import com.example.dailytimer.viewmodel.TimerState

@Composable
fun TodayResetSheet(
    timers: List<TimerState>,
    onDismiss: () -> Unit,
    onReset: (List<Long>) -> Unit
) {
    var selectedIds by remember { mutableStateOf(setOf<Long>()) }
    val allSelected = selectedIds.size == timers.size && timers.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BgPrimary, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(4.dp)
                .background(InputBorder, RoundedCornerShape(2.dp))
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "今日清零",
                style = Typography.titleLarge.copy(fontSize = 18.sp),
                color = TextDark
            )
            Text(
                text = if (allSelected) "取消全选" else "全选",
                style = Typography.bodySmall.copy(fontWeight = FontWeight.Medium, fontSize = 13.sp),
                color = StartBtn,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable {
                        selectedIds = if (allSelected) emptySet() else timers.map { it.id }.toSet()
                    }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "选择要清除今日累计的计时器",
            style = Typography.bodySmall.copy(fontSize = 13.sp),
            color = TextGray
        )
        Spacer(modifier = Modifier.height(12.dp))
        timers.forEach { timer ->
            val selected = selectedIds.contains(timer.id)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(
                        if (selected) DetailBg else CardBg,
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        1.dp,
                        if (selected) PauseBtn else Divider,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        selectedIds = if (selected) selectedIds - timer.id else selectedIds + timer.id
                    }
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CheckboxSmall(selected = selected)
                EmojiAvatar(iconType = timer.iconType, iconColor = timer.iconColor, modifier = Modifier.size(36.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = timer.name,
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),
                        color = TextDark
                    )
                    Text(
                        text = timer.dailyTotal,
                        style = Typography.bodySmall.copy(fontSize = 12.sp),
                        color = TextGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onDismiss,
                modifier = Modifier.weight(1f).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CardBg),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Divider)
            ) {
                Text(
                    text = "取消",
                    style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 15.sp),
                    color = TextGray
                )
            }
            Button(
                onClick = { onReset(selectedIds.toList()) },
                modifier = Modifier.weight(1f).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = StartBtn),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "清除 (${selectedIds.size})",
                    style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                    color = TextWhite
                )
            }
        }
    }
}

@Composable
private fun CheckboxSmall(selected: Boolean) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                if (selected) PauseBtn else Color.Transparent,
                RoundedCornerShape(6.dp)
            )
            .border(1.5.dp, if (selected) PauseBtn else InputBorder, RoundedCornerShape(6.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = TextWhite,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

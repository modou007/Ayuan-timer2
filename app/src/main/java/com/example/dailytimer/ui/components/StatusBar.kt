package com.example.dailytimer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailytimer.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StatusBar(modifier: Modifier = Modifier) {
    val time = remember { SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = time,
            style = Typography.labelMedium.copy(fontSize = 14.sp),
            color = TextDark
        )
        StatusIcons()
    }
}

@Composable
private fun StatusIcons() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Signal bars (simplified)
        Row(horizontalArrangement = Arrangement.spacedBy(1.dp), verticalAlignment = Alignment.Bottom) {
            listOf(4, 6, 9, 12).forEach { h ->
                Box(modifier = Modifier.width(3.dp).height(h.dp).background(TextDark))
            }
        }
        // WiFi icon (simplified)
        Box(modifier = Modifier.width(16.dp).height(12.dp)) {
            // placeholder
        }
        // Battery icon (simplified)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(22.dp).height(12.dp).border(1.dp, TextDark)) {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .width(14.dp)
                    .background(TextDark))
            }
            Box(modifier = Modifier.width(2.dp).height(4.dp).background(TextDark))
        }
    }
}

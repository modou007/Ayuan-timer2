package com.example.dailytimer.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailytimer.ui.theme.*

@Composable
fun CreateTimerDialog(
    onDismiss: () -> Unit,
    onCreate: (name: String, iconType: String, iconColor: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var iconType by remember { mutableStateOf("ball") }
    var iconColor by remember { mutableStateOf("red") }

    val emojiColors = listOf(
        "red" to Red, "yellow" to Yellow, "green" to Green,
        "blue" to Blue, "purple" to Purple
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BgPrimary, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Drag handle
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(4.dp)
                .background(InputBorder, RoundedCornerShape(2.dp))
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "新建计时器",
            style = Typography.titleLarge.copy(fontSize = 18.sp),
            color = TextDark
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "计时器名称",
            style = Typography.bodyMedium.copy(fontSize = 13.sp),
            color = TextGray
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(CardBg, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp),
            textStyle = TextStyle(fontSize = 14.sp, color = TextDark),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                    if (name.isEmpty()) {
                        Text(text = "例如：午睡计时", style = TextStyle(fontSize = 14.sp, color = Placeholder))
                    }
                    innerTextField()
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "选择图标",
            style = Typography.bodyMedium.copy(fontSize = 13.sp),
            color = TextGray
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Icon type tabs
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("彩色球" to "ball", "表情" to "face", "小动物" to "cat").forEach { (label, type) ->
                val selected = iconType == type
                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .background(if (selected) Accent else CardBg, RoundedCornerShape(16.dp))
                        .clickable { iconType = type }
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        style = Typography.bodyLarge.copy(
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        ),
                        color = if (selected) TextDark else TextGray
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        // Color balls
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            emojiColors.forEach { (cName, color) ->
                val selected = iconColor == cName
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .shadow(if (selected) 4.dp else 0.dp, CircleShape, spotColor = Color.Black.copy(alpha = 0.1f))
                        .background(color, CircleShape)
                        .clickable { iconColor = cName }
                        .padding(if (selected) 2.dp else 0.dp)
                        .background(if (selected) CardBg else Color.Transparent, CircleShape)
                        .padding(if (selected) 2.dp else 0.dp)
                        .background(color, CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DialogActionButton(
                text = "取消",
                bgColor = CardBg,
                textColor = TextGray,
                strokeColor = InputBorder,
                modifier = Modifier.weight(1f),
                onClick = onDismiss
            )
            DialogActionButton(
                text = "创建",
                bgColor = StartBtn,
                textColor = TextWhite,
                modifier = Modifier.weight(1f),
                onClick = { onCreate(name, iconType, iconColor) }
            )
        }
    }
}

@Composable
private fun DialogActionButton(
    text: String,
    bgColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color.Transparent,
    onClick: () -> Unit
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = bgColor),
        shape = RoundedCornerShape(16.dp),
        border = if (strokeColor != Color.Transparent) androidx.compose.foundation.BorderStroke(1.dp, strokeColor) else null
    ) {
        Text(
            text = text,
            style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
            color = textColor
        )
    }
}

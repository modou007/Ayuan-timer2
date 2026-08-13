package com.example.dailytimer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Use system fonts as fallback; for exact match load Poppins/Noto Sans SC via res/font
val Poppins = FontFamily.Default
val NotoSans = FontFamily.Default
val Sarasa = FontFamily.Default

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 56.sp,
        color = TextDark
    ),
    headlineLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = TextDark
    ),
    titleLarge = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = TextDark
    ),
    titleMedium = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        color = TextDark
    ),
    bodyLarge = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextDark
    ),
    bodyMedium = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        color = TextGray
    ),
    bodySmall = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextGray
    ),
    labelLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = TextWhite
    ),
    labelMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = TextDark
    ),
    labelSmall = TextStyle(
        fontFamily = NotoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        color = TextGray
    )
)

package com.smartlinksaver.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val LinkDeckTypography = Typography(
    titleLarge = TextStyle(
        fontWeight    = FontWeight.Bold,
        fontSize      = 20.sp,
        lineHeight    = 26.sp,
        letterSpacing = (-0.4).sp
    ),
    titleMedium = TextStyle(
        fontWeight    = FontWeight.SemiBold,
        fontSize      = 15.sp,
        lineHeight    = 21.sp,
        letterSpacing = (-0.2).sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 13.sp,
        lineHeight = 19.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize   = 12.sp,
        lineHeight = 17.sp
    ),
    labelMedium = TextStyle(
        fontWeight    = FontWeight.Medium,
        fontSize      = 11.sp,
        lineHeight    = 15.sp,
        letterSpacing = 0.3.sp
    )
)

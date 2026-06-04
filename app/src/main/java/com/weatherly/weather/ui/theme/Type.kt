package com.weatherly.weather.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val InterFontFamily = FontFamily.Default
val DisplayLargeStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Thin,
        fontSize = 80.sp,
        lineHeight = 88.sp,
        letterSpacing = (-1).sp,
    )

val DisplayMediumStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 56.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.5).sp,
    )

val HeadlineLargeStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp,
    )

val HeadlineMediumStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    )

val HeadlineSmallStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    )

val TitleLargeStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    )

val TitleMediumStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    )

val TitleSmallStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    )

val BodyLargeStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    )

val BodyMediumStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    )

val BodySmallStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )

val LabelLargeStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    )

val LabelMediumStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )

val LabelSmallStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    )

val CaptionStyle =
    TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color =
            androidx.compose.ui.graphics.Color.White
                .copy(alpha = 0.6f),
    )

val WeatherlyTypography =
    Typography(
        displayLarge = DisplayLargeStyle,
        displayMedium = DisplayMediumStyle,
        headlineLarge = HeadlineLargeStyle,
        headlineMedium = HeadlineMediumStyle,
        headlineSmall = HeadlineSmallStyle,
        titleLarge = TitleLargeStyle,
        titleMedium = TitleMediumStyle,
        titleSmall = TitleSmallStyle,
        bodyLarge = BodyLargeStyle,
        bodyMedium = BodyMediumStyle,
        bodySmall = BodySmallStyle,
        labelLarge = LabelLargeStyle,
        labelMedium = LabelMediumStyle,
        labelSmall = LabelSmallStyle,
    )

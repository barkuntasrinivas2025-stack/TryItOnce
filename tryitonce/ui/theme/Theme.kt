package com.TRY.tryitonce.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Brand500     = Color(0xFF6366F1)
val Brand400     = Color(0xFF818CF8)
val Brand700     = Color(0xFF4338CA)
val BrandSurface = Color(0xFFEEF2FF)
val Neutral50    = Color(0xFFF9FAFB)
val Neutral100   = Color(0xFFF3F4F6)
val Neutral600   = Color(0xFF4B5563)
val Neutral900   = Color(0xFF111827)
val ErrorRed     = Color(0xFFEF4444)
val SurfaceDark  = Color(0xFF1E1B4B)

private val LightColors = lightColorScheme(
    primary          = Brand500,
    onPrimary        = Neutral50,
    primaryContainer = BrandSurface,
    secondary        = Brand400,
    background       = Neutral50,
    surface          = Neutral100,
    onBackground     = Neutral900,
    onSurface        = Neutral600,
    error            = ErrorRed,
)

private val DarkColors = darkColorScheme(
    primary          = Brand400,
    onPrimary        = Neutral900,
    primaryContainer = SurfaceDark,
    background       = Neutral900,
    surface          = SurfaceDark,
    onBackground     = Neutral50,
    onSurface        = Neutral100,
    error            = ErrorRed,
)

val TryOnceTypography = Typography(
    headlineMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 28.sp),
    titleLarge     = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.SemiBold, fontSize = 22.sp),
    bodyLarge      = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium     = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelLarge     = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 14.sp),
)

@Composable
fun TryOnceTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography  = TryOnceTypography,
        content     = content,
    )
}

package com.financalcbr.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/* --------------------------------------------------------------------------- */
/* Light — fundo #F2F5F8, cards brancos, primaria #2F5D8C, textos #0B1F3B       */
/* --------------------------------------------------------------------------- */
private val LightColorScheme = lightColorScheme(
    primary = SlateBlue,
    onPrimary = IceWhite,
    primaryContainer = LightBlueGray,
    onPrimaryContainer = DeepNavy,
    inversePrimary = LightBlueGray,

    secondary = SlateBlueDeep,
    onSecondary = IceWhite,
    secondaryContainer = LightBlueGray,
    onSecondaryContainer = DeepNavy,

    tertiary = FinancePositiveLight,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFB7EFC7),
    onTertiaryContainer = Color(0xFF0B3D20),

    background = IceWhite,
    onBackground = DeepNavy,

    surface = Color.White,
    onSurface = DeepNavy,
    surfaceVariant = LightBlueGray,
    onSurfaceVariant = SlateBlueDeep,
    surfaceTint = SlateBlue,

    surfaceDim = Color(0xFFDCE4ED),
    surfaceBright = IceWhite,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = IceWhite,
    surfaceContainer = Color(0xFFE4EAF1),
    surfaceContainerHigh = Color(0xFFDDE5EE),
    surfaceContainerHighest = Color(0xFFD3DDE8),

    inverseSurface = DeepNavy,
    inverseOnSurface = IceWhite,

    error = FinanceNegativeLight,
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),

    outline = OutlineSoft,
    outlineVariant = Color(0xFFC4D2E0),
    scrim = DeepNavy
)

/* --------------------------------------------------------------------------- */
/* Dark — fundo #0B1F3B, cards #123A63/#1B4A78, primaria clara #C9D6E5          */
/* --------------------------------------------------------------------------- */
private val DarkColorScheme = darkColorScheme(
    primary = LightBlueGray,
    onPrimary = DeepNavy,
    primaryContainer = SlateBlue,
    onPrimaryContainer = IceWhite,
    inversePrimary = SlateBlue,

    secondary = OutlineSoft,
    onSecondary = DeepNavy,
    secondaryContainer = SlateBlueDeep,
    onSecondaryContainer = LightBlueGray,

    tertiary = FinancePositiveDark,
    onTertiary = DeepNavy,
    tertiaryContainer = Color(0xFF1B6B3A),
    onTertiaryContainer = Color(0xFFB7EFC7),

    background = DeepNavy,
    onBackground = IceWhite,

    surface = Navy,
    onSurface = IceWhite,
    surfaceVariant = NavyLift,
    onSurfaceVariant = LightBlueGray,
    surfaceTint = LightBlueGray,

    surfaceDim = DeepNavy,
    surfaceBright = NavyLift,
    surfaceContainerLowest = Color(0xFF081728),
    surfaceContainerLow = Color(0xFF0E2A4E),
    surfaceContainer = Navy,
    surfaceContainerHigh = Color(0xFF16396A),
    surfaceContainerHighest = NavyLift,

    inverseSurface = IceWhite,
    inverseOnSurface = DeepNavy,

    error = FinanceNegativeDark,
    onError = Color(0xFF410E0B),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),

    outline = OutlineDark,
    outlineVariant = Color(0xFF24406B),
    scrim = Color(0xFF070F1C)
)

/* --------------------------------------------------------------------------- */
/* Formas padronizadas: inputs/botoes 12dp, cards 16dp                          */
/* --------------------------------------------------------------------------- */
private val FinanCalcBRShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun FinanCalcBRTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = FinanCalcBRShapes,
        content = content
    )
}
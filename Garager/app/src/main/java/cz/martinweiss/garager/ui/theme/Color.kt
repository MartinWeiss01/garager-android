package cz.martinweiss.garager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF006D3C)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFF8BF9B1)
val md_theme_light_onPrimaryContainer = Color(0xFF00210E)
val md_theme_light_secondary = Color(0xFF4F6353)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFD2E8D4)
val md_theme_light_onSecondaryContainer = Color(0xFF0D1F13)
val md_theme_light_tertiary = Color(0xFF3A646F)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFBEEAF7)
val md_theme_light_onTertiaryContainer = Color(0xFF001F26)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFBFDF8)
val md_theme_light_onBackground = Color(0xFF191C19)
val md_theme_light_surface = Color(0xFFFBFDF8)
val md_theme_light_onSurface = Color(0xFF191C19)
val md_theme_light_surfaceVariant = Color(0xFFDDE5DB)
val md_theme_light_onSurfaceVariant = Color(0xFF414942)
val md_theme_light_outline = Color(0xFF717971)
val md_theme_light_inverseOnSurface = Color(0xFFF0F1EC)
val md_theme_light_inverseSurface = Color(0xFF2E312E)
val md_theme_light_inversePrimary = Color(0xFF6FDC97)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF006D3C)
val md_theme_light_outlineVariant = Color(0xFFC0C9BF)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFF6FDC97)
val md_theme_dark_onPrimary = Color(0xFF00391D)
val md_theme_dark_primaryContainer = Color(0xFF00522C)
val md_theme_dark_onPrimaryContainer = Color(0xFF8BF9B1)
val md_theme_dark_secondary = Color(0xFFB6CCB9)
val md_theme_dark_onSecondary = Color(0xFF223527)
val md_theme_dark_secondaryContainer = Color(0xFF384B3C)
val md_theme_dark_onSecondaryContainer = Color(0xFFD2E8D4)
val md_theme_dark_tertiary = Color(0xFFA2CDDA)
val md_theme_dark_onTertiary = Color(0xFF023640)
val md_theme_dark_tertiaryContainer = Color(0xFF214C57)
val md_theme_dark_onTertiaryContainer = Color(0xFFBEEAF7)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF191C19)
val md_theme_dark_onBackground = Color(0xFFE1E3DE)
val md_theme_dark_surface = Color(0xFF191C19)
val md_theme_dark_onSurface = Color(0xFFE1E3DE)
val md_theme_dark_surfaceVariant = Color(0xFF414942)
val md_theme_dark_onSurfaceVariant = Color(0xFFC0C9BF)
val md_theme_dark_outline = Color(0xFF8B938A)
val md_theme_dark_inverseOnSurface = Color(0xFF191C19)
val md_theme_dark_inverseSurface = Color(0xFFE1E3DE)
val md_theme_dark_inversePrimary = Color(0xFF006D3C)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFF6FDC97)
val md_theme_dark_outlineVariant = Color(0xFF414942)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFF00894D)

val light_onWarning = Color(0xFFFFA000)
val light_warningContainer = Color(0xFFFFECB3)

val dark_onWarning = Color(0xFFFFECB3)
val dark_warningContainer = Color(0xFFFFB300)

val surfaceSecondary = Color(0xFF1B1B1B)
val dark_surface_container = Color(0xFF333333)
val light_surface_container = Color(0xFF1A1A1A)

@Composable
fun warningContainerColor() = if(isSystemInDarkTheme()) dark_warningContainer else light_warningContainer

@Composable
fun onWarningColor() = if(isSystemInDarkTheme()) dark_onWarning else light_onWarning

@Composable
fun surfaceContainerColor() = if(isSystemInDarkTheme()) dark_surface_container else light_surface_container
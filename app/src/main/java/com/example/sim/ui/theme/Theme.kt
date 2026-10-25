package com.example.sim.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSimColors = staticCompositionLocalOf { DarkSimColors }
val LocalSimType = staticCompositionLocalOf { SimTypeScale }

/** Доступ к токенам: Sim.colors.surface, Sim.type.titleSm. Сырые hex в экранах не используем. */
object Sim {
    val colors: SimColors
        @Composable @ReadOnlyComposable get() = LocalSimColors.current
    val type: SimType
        @Composable @ReadOnlyComposable get() = LocalSimType.current
}

/** Тёмная тема по умолчанию. Переключатель темы появится в настройках (S40). */
@Composable
fun SimTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    val c = if (darkTheme) DarkSimColors else LightSimColors
    val scheme = if (darkTheme) {
        darkColorScheme(
            primary = c.accent, onPrimary = c.onAccent,
            background = c.bg, onBackground = c.text,
            surface = c.surface, onSurface = c.text,
            surfaceVariant = c.surfaceRaised, onSurfaceVariant = c.textMuted,
            outline = c.borderInput, outlineVariant = c.border,
            error = c.danger, onError = c.onDanger,
        )
    } else {
        lightColorScheme(
            primary = c.accent, onPrimary = c.onAccent,
            background = c.bg, onBackground = c.text,
            surface = c.surface, onSurface = c.text,
            surfaceVariant = c.surfaceRaised, onSurfaceVariant = c.textMuted,
            outline = c.borderInput, outlineVariant = c.border,
            error = c.danger, onError = c.onDanger,
        )
    }
    CompositionLocalProvider(LocalSimColors provides c, LocalSimType provides SimTypeScale) {
        MaterialTheme(colorScheme = scheme, content = content)
    }
}

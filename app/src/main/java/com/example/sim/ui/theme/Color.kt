package com.example.sim.ui.theme

import androidx.compose.ui.graphics.Color

/** Роли цвета. Имена совпадают с replica/design/tokens.json. При ребрендинге меняются только значения. */
data class SimColors(
    val bg: Color,
    val surface: Color,
    val surfaceRaised: Color,
    val border: Color,
    val borderInput: Color,
    val text: Color,
    val textMuted: Color,
    val accent: Color,
    val onAccent: Color,
    val danger: Color,
    val onDanger: Color,
    val success: Color,
    val onSuccess: Color,
    val warning: Color,
    val onWarning: Color,
    val resFood: Color,
    val resGold: Color,
    val resIron: Color,
    val resOil: Color,
    val resUranium: Color,
    val resEnergy: Color,
    val isDark: Boolean,
)

val DarkSimColors = SimColors(
    bg = Color(0xFF0E1318),
    surface = Color(0xFF151C24),
    surfaceRaised = Color(0xFF1D2733),
    border = Color(0xFF2B3846),
    borderInput = Color(0xFF7A8A9C),
    text = Color(0xFFE8EDF2),
    textMuted = Color(0xFFA3AFBD),
    accent = Color(0xFF5B9CFF),
    onAccent = Color(0xFF06121F),
    danger = Color(0xFFFF7A7A),
    onDanger = Color(0xFF1A0505),
    success = Color(0xFF3DD68C),
    onSuccess = Color(0xFF04140C),
    warning = Color(0xFFF2B84B),
    onWarning = Color(0xFF1A1100),
    resFood = Color(0xFF8BD450),
    resGold = Color(0xFFF2C14E),
    resIron = Color(0xFFB4C2D1),
    resOil = Color(0xFFE08A4A),
    resUranium = Color(0xFF5FE0B8),
    resEnergy = Color(0xFF5CCBFF),
    isDark = true,
)

val LightSimColors = SimColors(
    bg = Color(0xFFEEF1F5),
    surface = Color(0xFFFFFFFF),
    surfaceRaised = Color(0xFFF6F8FA),
    border = Color(0xFFD3D9E0),
    borderInput = Color(0xFF667180),
    text = Color(0xFF11161C),
    textMuted = Color(0xFF4A5562),
    accent = Color(0xFF1D5BD6),
    onAccent = Color(0xFFFFFFFF),
    danger = Color(0xFFB42318),
    onDanger = Color(0xFFFFFFFF),
    success = Color(0xFF14703F),
    onSuccess = Color(0xFFFFFFFF),
    warning = Color(0xFF8A5200),
    onWarning = Color(0xFFFFFFFF),
    resFood = Color(0xFF4A7F10),
    resGold = Color(0xFF9A6E00),
    resIron = Color(0xFF5F7183),
    resOil = Color(0xFFB05517),
    resUranium = Color(0xFF0C8463),
    resEnergy = Color(0xFF0B7AAE),
    isDark = false,
)

/** Карта всегда тёмная в обеих темах. Суша от океана отличается линией берега, а не заливкой. */
object MapColors {
    val ocean = Color(0xFF0A0F14)
    val land = Color(0xFF2F3B49)
    val border = Color(0xFF8A99AB)
    val own = Color(0xFF5B9CFF)
    val ally = Color(0xFF3DD68C)
    val hostile = Color(0xFFF2B84B)
    val war = Color(0xFFFF7A7A)
}

package com.example.sim.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// TODO: заменить на Inter, когда файлы шрифта лежат в res/font (см. tokens.json).
// Системный шрифт поддерживает кириллицу, поэтому M0 работает и так.
val SimFont: FontFamily = FontFamily.Default

class SimType(
    val xs: TextStyle,
    val sm: TextStyle,
    val base: TextStyle,
    val titleSm: TextStyle,
    val lg: TextStyle,
    val xl: TextStyle,
    val numMd: TextStyle,
    val numLg: TextStyle,
    val numXl: TextStyle,
)

private fun style(size: Int, line: Int, weight: FontWeight, tabular: Boolean = false) = TextStyle(
    fontFamily = SimFont,
    fontSize = size.sp,
    lineHeight = line.sp,
    fontWeight = weight,
    // tnum = цифры одной ширины, чтобы числа не дёргались при тике бюджета
    fontFeatureSettings = if (tabular) "tnum" else null,
)

val SimTypeScale = SimType(
    xs = style(12, 16, FontWeight.Medium),
    sm = style(14, 20, FontWeight.Normal),
    base = style(16, 24, FontWeight.Normal),
    titleSm = style(16, 22, FontWeight.SemiBold),
    lg = style(20, 28, FontWeight.SemiBold),
    xl = style(24, 30, FontWeight.Bold),
    numMd = style(16, 22, FontWeight.SemiBold, tabular = true),
    numLg = style(24, 28, FontWeight.Bold, tabular = true),
    numXl = style(32, 36, FontWeight.Bold, tabular = true),
)

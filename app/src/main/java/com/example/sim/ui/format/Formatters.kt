package com.example.sim.ui.format

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.example.sim.R
import com.example.sim.engine.GameDate
import com.example.sim.ui.components.formatNumber
import java.util.Locale
import kotlin.math.abs

/** Деньги целиком: разделитель групп, настоящий минус. */
fun formatMoney(v: Long): String = if (v < 0) "\u2212" + formatNumber(-v) else formatNumber(v)

/** Компактно для списков и чипов: 37,6 млн, 12 тыс. */
@Composable
fun compactNumber(v: Long): String {
    val a = abs(v)
    val sign = if (v < 0) "\u2212" else ""
    val locale = Locale.getDefault()
    return when {
        a >= 1_000_000_000L ->
            sign + String.format(locale, "%.1f", a / 1e9) + " " + stringResource(R.string.unit_billion_short)
        a >= 1_000_000L ->
            sign + String.format(locale, "%.1f", a / 1e6) + " " + stringResource(R.string.unit_million_short)
        a >= 10_000L ->
            sign + formatNumber(a / 1_000L) + " " + stringResource(R.string.unit_thousand_short)
        else -> sign + formatNumber(a)
    }
}

@Composable
fun formatDate(date: GameDate): String {
    val months = stringArrayResource(R.array.months_short)
    return "${date.day} ${months[date.month - 1]} ${date.year}"
}

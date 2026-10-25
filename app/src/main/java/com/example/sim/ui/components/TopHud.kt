package com.example.sim.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.sim.R
import com.example.sim.engine.GameSpeed
import com.example.sim.ui.format.formatMoney
import com.example.sim.ui.theme.Radius
import com.example.sim.ui.theme.Sim
import com.example.sim.ui.theme.Size
import com.example.sim.ui.theme.Space

/**
 * Верхняя панель (S05 и все экраны с навигацией). Две строки:
 * дата и скорость сверху, казна с дельтой снизу. Одной строкой на 360dp не помещается
 * ради кнопок 48dp (см. replica/design/components.md).
 */
@Composable
fun TopHud(
    dateText: String,
    speed: GameSpeed,
    onSpeed: (GameSpeed) -> Unit,
    treasury: Long,
    netPerDay: Long,
    modifier: Modifier = Modifier,
) {
    val c = Sim.colors
    val label = stringResource(R.string.hud_treasury)
    val perDay = stringResource(R.string.unit_per_day)
    val treasuryText = formatMoney(treasury)
    val spoken = "$label: $treasuryText, ${formatSigned(netPerDay)} $perDay"

    Column(modifier.fillMaxWidth().background(c.surface)) {
        Column(
            Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = Space.x4, vertical = Space.x2),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(dateText, style = Sim.type.titleSm, color = c.text)
                SpeedControl(speed, onSpeed)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .semantics(mergeDescendants = true) { contentDescription = spoken },
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(Space.x3),
            ) {
                Column {
                    Text(label, style = Sim.type.xs, color = c.textMuted)
                    Text(
                        treasuryText,
                        style = Sim.type.numXl,
                        color = if (treasury < 0) c.danger else c.text,
                    )
                }
                DeltaBadge(netPerDay, perDay)
            }
        }
        HorizontalDivider(color = c.border)
    }
}

@Composable
private fun SpeedControl(
    speed: GameSpeed,
    onSpeed: (GameSpeed) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = Sim.colors
    val shape = RoundedCornerShape(Radius.md)
    Row(
        modifier
            .clip(shape)
            .border(1.dp, c.borderInput, shape)
            .selectableGroup(),
    ) {
        GameSpeed.entries.forEach { option ->
            val selected = option == speed
            val description = stringResource(
                when (option) {
                    GameSpeed.Paused -> R.string.speed_pause
                    GameSpeed.X1 -> R.string.speed_x1
                    GameSpeed.X2 -> R.string.speed_x2
                    GameSpeed.X4 -> R.string.speed_x4
                },
            )
            val content = if (selected) c.onAccent else c.text
            Box(
                Modifier
                    .size(Size.touchMin)
                    .background(if (selected) c.accent else c.surface)
                    .selectable(selected = selected, role = Role.RadioButton, onClick = { onSpeed(option) })
                    .semantics { contentDescription = description },
                contentAlignment = Alignment.Center,
            ) {
                if (option == GameSpeed.Paused) {
                    Icon(Icons.Filled.Pause, contentDescription = null, tint = content)
                } else {
                    Text("${option.multiplier}x", style = Sim.type.numMd, color = content)
                }
            }
        }
    }
}

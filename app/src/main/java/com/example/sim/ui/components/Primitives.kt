package com.example.sim.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.sim.ui.theme.Radius
import com.example.sim.ui.theme.Sim
import com.example.sim.ui.theme.Size
import com.example.sim.ui.theme.Space
import java.text.NumberFormat

// Все строки приходят снаружи (из strings.xml). Здесь нет захардкоженного текста.

fun formatNumber(v: Long): String = NumberFormat.getIntegerInstance().format(v)

/** Знак всегда виден; минус настоящий (U+2212). */
fun formatSigned(v: Long): String = when {
    v > 0 -> "+" + formatNumber(v)
    v < 0 -> "\u2212" + formatNumber(-v)
    else -> "0"
}

// ---------- Panel ----------

@Composable
fun Panel(
    modifier: Modifier = Modifier,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val c = Sim.colors
    val shape = RoundedCornerShape(Radius.md)
    Column(
        modifier
            .fillMaxWidth()
            .clip(shape)
            .background(c.surface)
            .border(1.dp, c.border, shape)
            .padding(Space.x4),
        verticalArrangement = Arrangement.spacedBy(Space.x2),
    ) {
        if (title != null) Text(title, style = Sim.type.titleSm, color = c.text)
        content()
    }
}

// ---------- DeltaBadge: изменение за период. Знак и стрелка, цвет не единственный признак ----------

@Composable
fun DeltaBadge(value: Long, unit: String, modifier: Modifier = Modifier) {
    val c = Sim.colors
    val glyph: String
    val color: Color
    when {
        value > 0 -> { glyph = "\u25B2"; color = c.success }
        value < 0 -> { glyph = "\u25BC"; color = c.danger }
        else -> { glyph = "\u25A0"; color = c.textMuted }
    }
    val spoken = formatSigned(value) + " " + unit
    Text(
        text = "$glyph $spoken",
        style = Sim.type.numMd,
        color = color,
        modifier = modifier.semantics { contentDescription = spoken },
    )
}

// ---------- StatBar: бюджет, ресурс, одобрение, прогресс ----------

@Composable
fun StatBar(
    label: String,
    valueText: String,
    fraction: Float,
    barColor: Color,
    modifier: Modifier = Modifier,
    trailing: (@Composable () -> Unit)? = null,
) {
    val c = Sim.colors
    val trackShape = RoundedCornerShape(Radius.sm)
    Column(
        modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { contentDescription = "$label: $valueText" },
        verticalArrangement = Arrangement.spacedBy(Space.x1),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(label, style = Sim.type.sm, color = c.textMuted, modifier = Modifier.weight(1f))
            Row(horizontalArrangement = Arrangement.spacedBy(Space.x2), verticalAlignment = Alignment.CenterVertically) {
                Text(valueText, style = Sim.type.numMd, color = c.text)
                trailing?.invoke()
            }
        }
        Box(Modifier.fillMaxWidth().height(Size.barH).clip(trackShape).background(c.border)) {
            Box(
                Modifier
                    .fillMaxWidth(fraction.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(barColor),
            )
        }
    }
}

// ---------- LevelPips: уровень министерства ----------

@Composable
fun LevelPips(level: Int, max: Int, modifier: Modifier = Modifier) {
    val c = Sim.colors
    val shape = RoundedCornerShape(2.dp)
    Row(
        modifier.semantics(mergeDescendants = true) { contentDescription = "$level / $max" },
        horizontalArrangement = Arrangement.spacedBy(Space.x1),
    ) {
        repeat(max) { i ->
            val filled = i < level
            Box(
                Modifier
                    .size(Size.pip)
                    .clip(shape)
                    .background(if (filled) c.accent else Color.Transparent)
                    .border(1.dp, if (filled) c.accent else c.borderInput, shape),
            )
        }
    }
}

// ---------- RelationChip: цвет + символ + подпись ----------

enum class Relation { Ally, Neutral, Hostile, War }

@Composable
fun RelationChip(relation: Relation, label: String, modifier: Modifier = Modifier) {
    val c = Sim.colors
    val glyph: String
    val color: Color
    when (relation) {
        Relation.Ally -> { glyph = "\u25CF"; color = c.success }
        Relation.Neutral -> { glyph = "\u25CB"; color = c.textMuted }
        Relation.Hostile -> { glyph = "\u25B2"; color = c.warning }
        Relation.War -> { glyph = "\u2715"; color = c.danger }
    }
    val shape = RoundedCornerShape(percent = 50)
    Text(
        text = "$glyph $label",
        style = Sim.type.xs,
        color = color,
        modifier = modifier
            .clip(shape)
            .border(1.dp, color, shape)
            .padding(horizontal = Space.x2, vertical = Space.x1),
    )
}

// ---------- ResourceChip: полоса ресурсов на HUD ----------

enum class ResourceKind { Food, Gold, Iron, Oil, Uranium, Energy }

@Composable
fun resourceColor(kind: ResourceKind): Color {
    val c = Sim.colors
    return when (kind) {
        ResourceKind.Food -> c.resFood
        ResourceKind.Gold -> c.resGold
        ResourceKind.Iron -> c.resIron
        ResourceKind.Oil -> c.resOil
        ResourceKind.Uranium -> c.resUranium
        ResourceKind.Energy -> c.resEnergy
    }
}

@Composable
fun ResourceChip(
    kind: ResourceKind,
    name: String,
    amountText: String,
    netPerDay: Long,
    perDayUnit: String,
    modifier: Modifier = Modifier,
) {
    val c = Sim.colors
    val shape = RoundedCornerShape(percent = 50)
    val trend = when {
        netPerDay > 0 -> "\u25B2" to c.success
        netPerDay < 0 -> "\u25BC" to c.danger
        else -> "\u25A0" to c.textMuted
    }
    val spoken = "$name: $amountText, ${formatSigned(netPerDay)} $perDayUnit"
    Row(
        modifier
            .heightIn(min = Size.resourceStrip)
            .clip(shape)
            .background(c.surfaceRaised)
            .border(1.dp, c.border, shape)
            .padding(horizontal = Space.x3)
            .semantics(mergeDescendants = true) { contentDescription = spoken },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Space.x2),
    ) {
        Box(Modifier.size(Size.pip).background(resourceColor(kind)))
        Text(name, style = Sim.type.xs, color = c.textMuted)
        Text(amountText, style = Sim.type.numMd, color = c.text)
        Text(trend.first, style = Sim.type.xs, color = trend.second)
    }
}

// ---------- SimButton ----------

enum class ButtonKind { Primary, Secondary, Danger }

@Composable
fun SimButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    kind: ButtonKind = ButtonKind.Primary,
    enabled: Boolean = true,
) {
    val c = Sim.colors
    val shape = RoundedCornerShape(Radius.md)
    val sized = modifier.heightIn(min = Size.touchMin)
    if (kind == ButtonKind.Secondary) {
        OutlinedButton(
            onClick = onClick,
            modifier = sized,
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = c.accent, disabledContentColor = c.textMuted),
            border = BorderStroke(1.dp, if (enabled) c.accent else c.border),
        ) { Text(text, style = Sim.type.titleSm) }
    } else {
        val container = if (kind == ButtonKind.Danger) c.danger else c.accent
        val content = if (kind == ButtonKind.Danger) c.onDanger else c.onAccent
        Button(
            onClick = onClick,
            modifier = sized,
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = container,
                contentColor = content,
                disabledContainerColor = c.surfaceRaised,
                disabledContentColor = c.textMuted,
            ),
        ) { Text(text, style = Sim.type.titleSm) }
    }
}

// ---------- MinistryCard (S14) ----------

@Composable
fun MinistryCard(
    name: String,
    level: Int,
    maxLevel: Int,
    effectText: String,
    ministerText: String,
    upgradeLabel: String,
    canUpgrade: Boolean,
    blockedReason: String?,
    onUpgrade: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = Sim.colors
    Panel(modifier = modifier) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(name, style = Sim.type.titleSm, color = c.text, modifier = Modifier.weight(1f))
            LevelPips(level, maxLevel)
        }
        Text(effectText, style = Sim.type.sm, color = c.textMuted)
        Text(ministerText, style = Sim.type.sm, color = c.text)
        SimButton(upgradeLabel, onUpgrade, Modifier.fillMaxWidth(), enabled = canUpgrade)
        // Причина блокировки всегда текстом, а не только серой кнопкой
        if (!canUpgrade && blockedReason != null) {
            Text(blockedReason, style = Sim.type.xs, color = c.danger)
        }
    }
}

// ---------- CountryRow (список стран) ----------

@Composable
fun CountryRow(
    code: String,
    name: String,
    statLabel: String,
    statValue: String,
    relation: Relation,
    relationLabel: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = Sim.colors
    Row(
        modifier
            .fillMaxWidth()
            .heightIn(min = Size.rowMin)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = Space.x4, vertical = Space.x2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Space.x3),
    ) {
        // Заглушка флага: ISO-код в рамке, пока нет набора флагов
        Box(
            Modifier.size(Size.flagW, Size.flagH).border(1.dp, c.border, RoundedCornerShape(Radius.sm)),
            contentAlignment = Alignment.Center,
        ) { Text(code, style = Sim.type.xs, color = c.textMuted) }
        Column(Modifier.weight(1f)) {
            Text(name, style = Sim.type.titleSm, color = c.text)
            Text("$statLabel: $statValue", style = Sim.type.sm, color = c.textMuted)
        }
        RelationChip(relation, relationLabel)
    }
}

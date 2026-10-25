package com.example.sim.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.sim.R
import com.example.sim.engine.GameEngine
import com.example.sim.engine.GameState
import com.example.sim.nav.ScreenRegistry
import com.example.sim.nav.Section
import com.example.sim.ui.components.DeltaBadge
import com.example.sim.ui.components.NavRow
import com.example.sim.ui.components.NavRowDivider
import com.example.sim.ui.components.Panel
import com.example.sim.ui.components.ResourceStrip
import com.example.sim.ui.components.StatBar
import com.example.sim.ui.format.compactNumber
import com.example.sim.ui.format.countryNameRes
import com.example.sim.ui.format.formatMoney
import com.example.sim.ui.format.subtitleRes
import com.example.sim.ui.format.titleRes
import com.example.sim.ui.theme.Sim
import com.example.sim.ui.theme.Size
import com.example.sim.ui.theme.Space

/** S05: главный экран управления государством. */
@Composable
fun HomeScreen(
    state: GameState,
    onOpenSection: (Section) -> Unit,
    onOpenScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = Sim.colors
    val p = state.player
    val perMonth = stringResource(R.string.unit_per_month)
    val income = GameEngine.incomePerMonth(p)
    val expense = GameEngine.expensePerMonth(p)
    val balance = income - expense
    val scale = maxOf(income, expense, 1L).toFloat()
    val approvalColor = when {
        p.approvalBp < 3_000 -> c.danger
        p.approvalBp < 6_000 -> c.warning
        else -> c.success
    }
    val approvalPercent = p.approvalBp / 100

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = Space.x3),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Space.x3),
    ) {
        ResourceStrip(p.resources)

        Column(
            Modifier
                .widthIn(max = Size.contentMax)
                .fillMaxWidth()
                .padding(horizontal = Space.x4),
            verticalArrangement = Arrangement.spacedBy(Space.x3),
        ) {
            Text(stringResource(countryNameRes(p.code)), style = Sim.type.lg, color = c.text)

            Panel(title = stringResource(R.string.home_budget_title)) {
                StatBar(
                    label = stringResource(R.string.budget_income),
                    valueText = formatMoney(income),
                    fraction = income / scale,
                    barColor = c.success,
                )
                StatBar(
                    label = stringResource(R.string.budget_expense),
                    valueText = formatMoney(expense),
                    fraction = expense / scale,
                    barColor = c.danger,
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(stringResource(R.string.budget_balance), style = Sim.type.sm, color = c.textMuted)
                    DeltaBadge(balance, perMonth)
                }
                val runway = GameEngine.runwayDays(p)
                if (runway != null) {
                    Text(
                        stringResource(R.string.runway_days, runway),
                        style = Sim.type.sm,
                        color = c.danger,
                    )
                }
            }

            Panel(title = stringResource(R.string.home_people_title)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(stringResource(R.string.people_population), style = Sim.type.sm, color = c.textMuted)
                    Text(compactNumber(p.population), style = Sim.type.numMd, color = c.text)
                }
                StatBar(
                    label = stringResource(R.string.people_approval),
                    valueText = stringResource(R.string.percent_value, approvalPercent),
                    fraction = p.approvalBp / 10_000f,
                    barColor = approvalColor,
                )
            }

            Panel(title = stringResource(R.string.home_sections_title)) {
                val sections = listOf(Section.Economy, Section.Ministries, Section.Army, Section.World)
                sections.forEachIndexed { i, section ->
                    if (i > 0) NavRowDivider()
                    NavRow(
                        title = stringResource(section.titleRes()),
                        subtitle = section.subtitleRes()?.let { stringResource(it) },
                        onClick = { onOpenSection(section) },
                    )
                }
            }

            Panel(title = stringResource(R.string.home_country_title)) {
                ScreenRegistry.hubItems(Section.Country).forEachIndexed { i, spec ->
                    if (i > 0) NavRowDivider()
                    NavRow(
                        title = stringResource(spec.title),
                        subtitle = null,
                        onClick = { onOpenScreen(spec.id) },
                    )
                }
            }
        }
    }
}

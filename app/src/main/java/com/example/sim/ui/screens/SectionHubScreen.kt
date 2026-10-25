package com.example.sim.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.example.sim.nav.ScreenRegistry
import com.example.sim.nav.Section
import com.example.sim.ui.components.NavRow
import com.example.sim.ui.components.NavRowDivider
import com.example.sim.ui.components.Panel
import com.example.sim.ui.format.titleRes
import com.example.sim.ui.theme.Sim
import com.example.sim.ui.theme.Size
import com.example.sim.ui.theme.Space

/** Список экранов раздела. Пока все они заглушки: у каждой строки подпись с этапом. */
@Composable
fun SectionHubScreen(
    section: Section,
    onOpenScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = Sim.colors
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Space.x4),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            Modifier.widthIn(max = Size.contentMax).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Space.x3),
        ) {
            Text(stringResource(section.titleRes()), style = Sim.type.lg, color = c.text)
            Panel {
                ScreenRegistry.hubItems(section).forEachIndexed { i, spec ->
                    if (i > 0) NavRowDivider()
                    NavRow(
                        title = stringResource(spec.title),
                        subtitle = stringResource(R.string.stub_milestone, spec.milestone),
                        onClick = { onOpenScreen(spec.id) },
                    )
                }
            }
        }
    }
}

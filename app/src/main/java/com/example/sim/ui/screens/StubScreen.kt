package com.example.sim.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.sim.R
import com.example.sim.nav.ScreenSpec
import com.example.sim.ui.components.ButtonKind
import com.example.sim.ui.components.Panel
import com.example.sim.ui.components.SimButton
import com.example.sim.ui.theme.Sim
import com.example.sim.ui.theme.Size
import com.example.sim.ui.theme.Space

/** Заглушка для экранов, которые придут на следующих этапах. */
@Composable
fun StubScreen(
    spec: ScreenSpec?,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = Sim.colors
    Column(
        modifier.fillMaxSize().padding(Space.x4),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            Modifier.widthIn(max = Size.contentMax).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Space.x3),
        ) {
            Text(
                text = stringResource(spec?.title ?: R.string.stub_unknown),
                style = Sim.type.lg,
                color = c.text,
            )
            Panel {
                Text(stringResource(R.string.stub_body), style = Sim.type.base, color = c.text)
                if (spec != null) {
                    Text(
                        stringResource(R.string.stub_milestone, spec.milestone),
                        style = Sim.type.sm,
                        color = c.textMuted,
                    )
                }
            }
            SimButton(
                text = stringResource(R.string.action_back),
                onClick = onBack,
                kind = ButtonKind.Secondary,
            )
        }
    }
}

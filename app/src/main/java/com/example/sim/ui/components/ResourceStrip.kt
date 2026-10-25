package com.example.sim.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.sim.R
import com.example.sim.engine.ResourceStock
import com.example.sim.engine.ResourceType
import com.example.sim.ui.format.compactNumber
import com.example.sim.ui.format.nameRes
import com.example.sim.ui.format.toKind
import com.example.sim.ui.theme.Space

@Composable
fun ResourceStrip(
    resources: Map<ResourceType, ResourceStock>,
    modifier: Modifier = Modifier,
) {
    val perDay = stringResource(R.string.unit_per_day)
    Row(
        modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = Space.x4),
        horizontalArrangement = Arrangement.spacedBy(Space.x2),
    ) {
        ResourceType.entries.forEach { type ->
            val r = resources[type] ?: ResourceStock(0L, 0L)
            ResourceChip(
                kind = type.toKind(),
                name = stringResource(type.nameRes()),
                amountText = compactNumber(r.stock),
                netPerDay = r.netPerDay,
                perDayUnit = perDay,
            )
        }
    }
}

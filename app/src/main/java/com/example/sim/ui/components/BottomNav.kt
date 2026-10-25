package com.example.sim.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sim.nav.Section
import com.example.sim.ui.format.icon
import com.example.sim.ui.format.titleRes
import com.example.sim.ui.theme.Sim

@Composable
fun BottomNav(selected: Section, onSelect: (Section) -> Unit) {
    val c = Sim.colors
    NavigationBar(containerColor = c.surface, contentColor = c.text, tonalElevation = 0.dp) {
        Section.entries.forEach { section ->
            NavigationBarItem(
                selected = section == selected,
                onClick = { onSelect(section) },
                icon = { Icon(section.icon(), contentDescription = null) },
                label = { Text(stringResource(section.titleRes()), style = Sim.type.xs, maxLines = 1) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = c.onAccent,
                    selectedTextColor = c.accent,
                    indicatorColor = c.accent,
                    unselectedIconColor = c.textMuted,
                    unselectedTextColor = c.textMuted,
                ),
            )
        }
    }
}

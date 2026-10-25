package com.example.sim.nav

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sim.data.GameViewModel
import com.example.sim.engine.GameEngine
import com.example.sim.ui.components.BottomNav
import com.example.sim.ui.components.TopHud
import com.example.sim.ui.format.formatDate
import com.example.sim.ui.screens.HomeScreen
import com.example.sim.ui.screens.SectionHubScreen
import com.example.sim.ui.screens.StubScreen
import com.example.sim.ui.theme.Sim

@Composable
fun AppRoot(vm: GameViewModel) {
    val navController = rememberNavController()
    val state by vm.state.collectAsStateWithLifecycle()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentSection = sectionOf(backStackEntry)

    Scaffold(
        containerColor = Sim.colors.bg,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopHud(
                dateText = formatDate(GameEngine.dateOf(state.currentDay)),
                speed = state.speed,
                onSpeed = vm::setSpeed,
                treasury = state.player.treasury,
                netPerDay = GameEngine.netPerDay(state.player),
            )
        },
        bottomBar = {
            BottomNav(
                selected = currentSection,
                onSelect = { navigateToSection(navController, it) },
            )
        },
    ) { inner ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(inner),
        ) {
            composable(Routes.HOME) {
                HomeScreen(
                    state = state,
                    onOpenSection = { navigateToSection(navController, it) },
                    onOpenScreen = { navController.navigate(Routes.screen(it)) },
                )
            }
            composable(
                route = Routes.HUB,
                arguments = listOf(navArgument(Routes.ARG_SECTION) { type = NavType.StringType }),
            ) { entry ->
                val section = entry.arguments
                    ?.getString(Routes.ARG_SECTION)
                    ?.let { name -> Section.entries.firstOrNull { it.name == name } }
                    ?: Section.Country
                SectionHubScreen(
                    section = section,
                    onOpenScreen = { navController.navigate(Routes.screen(it)) },
                )
            }
            composable(
                route = Routes.SCREEN,
                arguments = listOf(navArgument(Routes.ARG_ID) { type = NavType.StringType }),
            ) { entry ->
                val id = entry.arguments?.getString(Routes.ARG_ID).orEmpty()
                StubScreen(
                    spec = ScreenRegistry.byId(id),
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}

private fun sectionOf(entry: NavBackStackEntry?): Section {
    val route = entry?.destination?.route ?: return Section.Country
    return when (route) {
        Routes.HOME -> Section.Country
        Routes.HUB -> entry.arguments
            ?.getString(Routes.ARG_SECTION)
            ?.let { name -> Section.entries.firstOrNull { it.name == name } }
            ?: Section.Country
        Routes.SCREEN -> ScreenRegistry.byId(entry.arguments?.getString(Routes.ARG_ID).orEmpty())
            ?.section ?: Section.Country
        else -> Section.Country
    }
}

private fun navigateToSection(nav: NavHostController, section: Section) {
    val route = if (section == Section.Country) Routes.HOME else Routes.hub(section)
    nav.navigate(route) {
        popUpTo(nav.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

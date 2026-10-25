package com.example.sim.nav

import androidx.annotation.StringRes
import com.example.sim.R

/** Пять разделов нижней навигации. Соответствие шести категориям меню оригинала описано в replica/design/components.md. */
enum class Section { Country, Economy, Ministries, Army, World }

/**
 * Один экран из инвентаря recon (S01-S40; S41 «магазин» в skip).
 * milestone: этап из replica/architecture.md, на котором экран перестаёт быть заглушкой.
 */
data class ScreenSpec(
    val id: String,
    @StringRes val title: Int,
    val section: Section?,
    val inHub: Boolean,
    val milestone: String,
)

object ScreenRegistry {
    val all: List<ScreenSpec> = listOf(
        ScreenSpec("S01", R.string.screen_s01, null, false, "M0"),
        ScreenSpec("S02", R.string.screen_s02, null, false, "M0"),
        ScreenSpec("S03", R.string.screen_s03, null, false, "M0"),
        ScreenSpec("S04", R.string.screen_s04, null, false, "M1"),
        ScreenSpec("S05", R.string.screen_s05, Section.Country, false, "M0"),
        ScreenSpec("S06", R.string.screen_s06, Section.Country, true, "M1"),
        ScreenSpec("S07", R.string.screen_s07, Section.Economy, true, "M0"),
        ScreenSpec("S08", R.string.screen_s08, Section.Economy, true, "M6"),
        ScreenSpec("S09", R.string.screen_s09, Section.Economy, true, "M1"),
        ScreenSpec("S10", R.string.screen_s10, Section.Economy, true, "M4"),
        ScreenSpec("S11", R.string.screen_s11, Section.Economy, false, "M4"),
        ScreenSpec("S12", R.string.screen_s12, Section.Economy, true, "M1"),
        ScreenSpec("S13", R.string.screen_s13, Section.Economy, true, "M6"),
        ScreenSpec("S14", R.string.screen_s14, Section.Ministries, true, "M2"),
        ScreenSpec("S15", R.string.screen_s15, Section.Ministries, false, "M2"),
        ScreenSpec("S16", R.string.screen_s16, Section.Ministries, true, "M2"),
        ScreenSpec("S17", R.string.screen_s17, Section.Economy, true, "M2"),
        ScreenSpec("S18", R.string.screen_s18, Section.Army, true, "M5"),
        ScreenSpec("S19", R.string.screen_s19, Section.Army, true, "M5"),
        ScreenSpec("S20", R.string.screen_s20, Section.Army, true, "M6"),
        ScreenSpec("S21", R.string.screen_s21, Section.Ministries, true, "M6"),
        ScreenSpec("S22", R.string.screen_s22, Section.Army, true, "M7"),
        ScreenSpec("S23", R.string.screen_s23, Section.Army, true, "M7"),
        ScreenSpec("S24", R.string.screen_s24, Section.World, true, "M4"),
        ScreenSpec("S25", R.string.screen_s25, Section.World, false, "M4"),
        ScreenSpec("S26", R.string.screen_s26, Section.World, true, "M4"),
        ScreenSpec("S27", R.string.screen_s27, Section.World, true, "M6"),
        ScreenSpec("S28", R.string.screen_s28, Section.Army, true, "M5"),
        ScreenSpec("S29", R.string.screen_s29, Section.Army, false, "M6"),
        ScreenSpec("S30", R.string.screen_s30, Section.Army, false, "M5"),
        ScreenSpec("S31", R.string.screen_s31, Section.World, true, "M6"),
        ScreenSpec("S32", R.string.screen_s32, Section.Country, true, "M7"),
        ScreenSpec("S33", R.string.screen_s33, Section.Country, true, "M7"),
        ScreenSpec("S34", R.string.screen_s34, null, false, "M3"),
        ScreenSpec("S35", R.string.screen_s35, Section.Country, true, "M3"),
        ScreenSpec("S36", R.string.screen_s36, Section.Country, true, "M5"),
        ScreenSpec("S37", R.string.screen_s37, Section.Country, true, "M5"),
        ScreenSpec("S38", R.string.screen_s38, null, false, "M5"),
        ScreenSpec("S39", R.string.screen_s39, Section.Country, true, "M0"),
        ScreenSpec("S40", R.string.screen_s40, Section.Country, true, "M1"),
    )

    private val index: Map<String, ScreenSpec> = all.associateBy { it.id }

    fun byId(id: String): ScreenSpec? = index[id]

    fun hubItems(section: Section): List<ScreenSpec> =
        all.filter { it.inHub && it.section == section }
}

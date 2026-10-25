package com.example.sim.ui.format

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Shield
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.sim.R
import com.example.sim.engine.ResourceType
import com.example.sim.nav.Section
import com.example.sim.ui.components.ResourceKind

fun ResourceType.toKind(): ResourceKind = when (this) {
    ResourceType.Food -> ResourceKind.Food
    ResourceType.Gold -> ResourceKind.Gold
    ResourceType.Iron -> ResourceKind.Iron
    ResourceType.Oil -> ResourceKind.Oil
    ResourceType.Uranium -> ResourceKind.Uranium
    ResourceType.Energy -> ResourceKind.Energy
}

@StringRes
fun ResourceType.nameRes(): Int = when (this) {
    ResourceType.Food -> R.string.res_food
    ResourceType.Gold -> R.string.res_gold
    ResourceType.Iron -> R.string.res_iron
    ResourceType.Oil -> R.string.res_oil
    ResourceType.Uranium -> R.string.res_uranium
    ResourceType.Energy -> R.string.res_energy
}

@StringRes
fun Section.titleRes(): Int = when (this) {
    Section.Country -> R.string.nav_country
    Section.Economy -> R.string.nav_economy
    Section.Ministries -> R.string.nav_ministries
    Section.Army -> R.string.nav_army
    Section.World -> R.string.nav_world
}

/** Подпись раздела на главном экране; у раздела «Страна» её нет. */
@StringRes
fun Section.subtitleRes(): Int? = when (this) {
    Section.Country -> null
    Section.Economy -> R.string.sec_economy_sub
    Section.Ministries -> R.string.sec_ministries_sub
    Section.Army -> R.string.sec_army_sub
    Section.World -> R.string.sec_world_sub
}

fun Section.icon(): ImageVector = when (this) {
    Section.Country -> Icons.Filled.Home
    Section.Economy -> Icons.Filled.AccountBalanceWallet
    Section.Ministries -> Icons.Filled.AccountBalance
    Section.Army -> Icons.Filled.Shield
    Section.World -> Icons.Filled.Public
}

@StringRes
fun countryNameRes(code: String): Int = when (code) {
    "PL" -> R.string.country_pl
    else -> R.string.country_unknown
}

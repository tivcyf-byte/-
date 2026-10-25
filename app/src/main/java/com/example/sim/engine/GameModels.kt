package com.example.sim.engine

// Чистый Kotlin: в пакете engine нет импортов android, поэтому он тестируется обычным JUnit.

enum class ResourceType { Food, Gold, Iron, Oil, Uranium, Energy }

/** Запас и чистый прирост в день (может быть отрицательным). */
data class ResourceStock(val stock: Long, val netPerDay: Long)

/** Скорости из replica/architecture.md: 0 = пауза. */
enum class GameSpeed(val multiplier: Int) { Paused(0), X1(1), X2(2), X4(4) }

/** Игровой календарь: 12 месяцев по 30 дней. month 1..12, day 1..30. */
data class GameDate(val year: Int, val month: Int, val day: Int)

data class CountryState(
    val code: String,
    val population: Long,
    /** Одобрение в базисных пунктах: 10000 = 100%. */
    val approvalBp: Int,
    /** Казна в золоте. Может быть отрицательной (долг). */
    val treasury: Long,
    /** Ставка подоходного налога в базисных пунктах. */
    val taxIncomeBp: Int,
    /** Расходы за день (министерства, содержание). Заглушка до этапа M2. */
    val upkeepPerDay: Long,
    val resources: Map<ResourceType, ResourceStock>,
)

data class GameState(
    val currentDay: Int,
    val speed: GameSpeed,
    val player: CountryState,
)

package com.example.sim.engine

object GameEngine {

    private const val BASIS = 10_000L

    fun dateOf(day: Int): GameDate {
        val d = day.coerceAtLeast(0)
        val daysInYear = Balance.DAYS_IN_MONTH * Balance.MONTHS_IN_YEAR
        val dayOfYear = d % daysInYear
        return GameDate(
            year = Balance.START_YEAR + d / daysInYear,
            month = dayOfYear / Balance.DAYS_IN_MONTH + 1,
            day = dayOfYear % Balance.DAYS_IN_MONTH + 1,
        )
    }

    fun incomePerDay(c: CountryState): Long =
        c.population * Balance.TAX_MILLI_GOLD_PER_CITIZEN * c.taxIncomeBp / (1000L * BASIS)

    fun netPerDay(c: CountryState): Long = incomePerDay(c) - c.upkeepPerDay

    fun incomePerMonth(c: CountryState): Long = incomePerDay(c) * Balance.DAYS_IN_MONTH

    fun expensePerMonth(c: CountryState): Long = c.upkeepPerDay * Balance.DAYS_IN_MONTH

    /** На сколько дней хватит казны при текущем дефиците; null, если страна не теряет деньги. */
    fun runwayDays(c: CountryState): Int? {
        val net = netPerDay(c)
        if (net >= 0) return null
        if (c.treasury <= 0) return 0
        return (c.treasury / -net).toInt()
    }

    /** Один игровой день. Чистая функция: то же входное состояние даёт тот же результат. */
    fun advanceDay(state: GameState): GameState {
        val p = state.player
        val resources = p.resources.mapValues { (_, r) ->
            r.copy(stock = (r.stock + r.netPerDay).coerceAtLeast(0L))
        }
        return state.copy(
            currentDay = state.currentDay + 1,
            player = p.copy(treasury = p.treasury + netPerDay(p), resources = resources),
        )
    }
}

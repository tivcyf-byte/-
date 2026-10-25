package com.example.sim.engine

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class GameEngineTest {

    @Test
    fun calendarStartsOnFirstOfJanuary() {
        assertEquals(GameDate(2026, 1, 1), GameEngine.dateOf(0))
    }

    @Test
    fun monthHasThirtyDays() {
        assertEquals(GameDate(2026, 1, 30), GameEngine.dateOf(29))
        assertEquals(GameDate(2026, 2, 1), GameEngine.dateOf(30))
    }

    @Test
    fun yearRollsOverAfterTwelveMonths() {
        assertEquals(GameDate(2026, 12, 30), GameEngine.dateOf(359))
        assertEquals(GameDate(2027, 1, 1), GameEngine.dateOf(360))
    }

    @Test
    fun negativeDayIsTreatedAsStart() {
        assertEquals(GameDate(2026, 1, 1), GameEngine.dateOf(-5))
    }

    @Test
    fun advanceDayMovesCalendarAndTreasury() {
        val start = SeedData.newGame()
        val next = GameEngine.advanceDay(start)
        assertEquals(1, next.currentDay)
        assertEquals(start.player.treasury + GameEngine.netPerDay(start.player), next.player.treasury)
    }

    @Test
    fun advanceDayAppliesResourceChangeAndKeepsStockNonNegative() {
        val base = SeedData.newGame()
        val poor = base.copy(
            player = base.player.copy(
                resources = mapOf(
                    ResourceType.Oil to ResourceStock(5, -100),
                    ResourceType.Food to ResourceStock(10, 7),
                ),
            ),
        )
        val next = GameEngine.advanceDay(poor).player.resources
        assertEquals(0L, next.getValue(ResourceType.Oil).stock)
        assertEquals(17L, next.getValue(ResourceType.Food).stock)
    }

    @Test
    fun sameInputGivesSameOutput() {
        val s = SeedData.newGame()
        assertEquals(GameEngine.advanceDay(s), GameEngine.advanceDay(s))
    }

    @Test
    fun incomeScalesWithTaxRate() {
        val p = SeedData.newGame().player
        val doubled = p.copy(taxIncomeBp = p.taxIncomeBp * 2)
        assertEquals(GameEngine.incomePerDay(p) * 2, GameEngine.incomePerDay(doubled))
    }

    @Test
    fun monthlyFiguresAreThirtyDays() {
        val p = SeedData.newGame().player
        assertEquals(GameEngine.incomePerDay(p) * 30, GameEngine.incomePerMonth(p))
        assertEquals(p.upkeepPerDay * 30, GameEngine.expensePerMonth(p))
    }

    @Test
    fun runwayIsNullWhenTreasuryGrows() {
        assertNull(GameEngine.runwayDays(SeedData.newGame().player))
    }

    @Test
    fun runwayCountsDaysUntilEmptyWhenInDeficit() {
        val p = SeedData.newGame().player
        val deficit = p.copy(upkeepPerDay = GameEngine.incomePerDay(p) + 1_000L, treasury = 100_000L)
        assertEquals(100, GameEngine.runwayDays(deficit))
    }

    @Test
    fun runwayIsZeroWhenTreasuryAlreadyEmpty() {
        val p = SeedData.newGame().player
        val broke = p.copy(upkeepPerDay = GameEngine.incomePerDay(p) + 1L, treasury = -50L)
        assertNotNull(GameEngine.runwayDays(broke))
        assertEquals(0, GameEngine.runwayDays(broke))
    }
}

package com.example.sim.engine

/** Стартовые данные M0: одна страна. Выбор из 20-30 стран придёт с экраном S02. */
object SeedData {
    fun newGame(): GameState = GameState(
        currentDay = 0,
        speed = GameSpeed.Paused,
        player = CountryState(
            code = "PL",
            population = 37_600_000L,
            approvalBp = 6_200,
            treasury = 1_250_000L,
            taxIncomeBp = 2_500,
            upkeepPerDay = 80_000L,
            resources = mapOf(
                ResourceType.Food to ResourceStock(4_200, 120),
                ResourceType.Gold to ResourceStock(86_000, -300),
                ResourceType.Iron to ResourceStock(1_900, 0),
                ResourceType.Oil to ResourceStock(640, 35),
                ResourceType.Uranium to ResourceStock(12, 1),
                ResourceType.Energy to ResourceStock(310, -20),
            ),
        ),
    )
}

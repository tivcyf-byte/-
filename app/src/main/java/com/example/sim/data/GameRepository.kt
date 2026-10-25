package com.example.sim.data

import com.example.sim.engine.GameEngine
import com.example.sim.engine.GameSpeed
import com.example.sim.engine.GameState
import com.example.sim.engine.SeedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Слой данных. Сейчас фейковый (в памяти). Реализация на Room появится позже
 * с теми же сигнатурами, поэтому экраны при замене не меняются.
 */
interface GameRepository {
    val state: StateFlow<GameState>
    fun setSpeed(speed: GameSpeed)
    fun advanceDay()
}

class InMemoryGameRepository(initial: GameState = SeedData.newGame()) : GameRepository {
    private val mutable = MutableStateFlow(initial)
    override val state: StateFlow<GameState> = mutable.asStateFlow()

    override fun setSpeed(speed: GameSpeed) {
        mutable.update { it.copy(speed = speed) }
    }

    override fun advanceDay() {
        mutable.update { GameEngine.advanceDay(it) }
    }
}

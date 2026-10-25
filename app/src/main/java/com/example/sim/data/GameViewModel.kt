package com.example.sim.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sim.engine.GameSpeed
import com.example.sim.engine.GameState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val repo: GameRepository = InMemoryGameRepository()

    val state: StateFlow<GameState> = repo.state

    init {
        // Цикл тика: при смене скорости предыдущий цикл отменяется и стартует новый.
        viewModelScope.launch {
            repo.state.map { it.speed }.distinctUntilChanged().collectLatest { speed ->
                if (speed != GameSpeed.Paused) {
                    while (true) {
                        delay(BASE_DAY_MS / speed.multiplier)
                        repo.advanceDay()
                    }
                }
            }
        }
    }

    fun setSpeed(speed: GameSpeed) = repo.setSpeed(speed)

    /** Приложение в фоне означает игра на паузе. */
    fun pauseForBackground() {
        if (repo.state.value.speed != GameSpeed.Paused) repo.setSpeed(GameSpeed.Paused)
    }

    private companion object {
        const val BASE_DAY_MS = 1_000L
    }
}

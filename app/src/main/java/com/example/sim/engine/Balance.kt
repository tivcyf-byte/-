package com.example.sim.engine

/** Все числа баланса в одном месте, чтобы подбирать их без правки логики. Значения временные. */
object Balance {
    const val DAYS_IN_MONTH = 30
    const val MONTHS_IN_YEAR = 12
    const val START_YEAR = 2026

    /** Доход с одного жителя в день при налоге 100%, в тысячных долях золота. */
    const val TAX_MILLI_GOLD_PER_CITIZEN = 10L
}

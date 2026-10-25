package com.example.sim.nav

object Routes {
    /** S05, вкладка «Страна». */
    const val HOME = "home"
    const val HUB = "hub/{section}"
    const val SCREEN = "screen/{id}"

    const val ARG_SECTION = "section"
    const val ARG_ID = "id"

    fun hub(section: Section) = "hub/${section.name}"
    fun screen(id: String) = "screen/$id"
}

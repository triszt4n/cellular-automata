package com.triszt4n.wireworld.main

import com.triszt4n.wireworld.model.BoardApi
import com.triszt4n.wireworld.model.rulesets.WireWorldTile
import tornadofx.*

class MainController : Controller() {
    val mainView: MainView by inject()
    val api: BoardApi<WireWorldTile> = BoardApi()

    fun play() {

    }

    fun pause() {

    }

    fun reset() {

    }
}

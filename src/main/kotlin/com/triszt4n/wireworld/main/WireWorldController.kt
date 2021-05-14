package com.triszt4n.wireworld.main

import com.triszt4n.wireworld.model.BoardApi
import com.triszt4n.wireworld.model.rulesets.WireWorldTile
import java.io.File

class WireWorldController : AbstractController() {
    private val mainView: AbstractView by inject()
    private val api: BoardApi<WireWorldTile> = BoardApi()

    override fun new(rowsString: String, colsString: String) {
        api.new(rowsString.toInt(), colsString.toInt()) { WireWorldTile() }
    }

    override fun load() {
        val file: File = getLoadedFile() ?: return
        api.loadFromFile(file) { WireWorldTile() }
    }

    override fun save() {
        val file: File = getSavedFile() ?: return
        api.saveToFile(file)
    }
}

package com.triszt4n.wireworld.main

import com.triszt4n.wireworld.model.BoardApi
import com.triszt4n.wireworld.model.InputModeType
import com.triszt4n.wireworld.model.forEach
import com.triszt4n.wireworld.model.rulesets.WireWorldTile
import com.triszt4n.wireworld.model.rulesets.WireWorldTileType
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import java.io.File
import java.util.*

class WireWorldController : AbstractController() {
    private val wireWorldView: WireWorldView by inject()
    private val api: BoardApi<WireWorldTile> = BoardApi()

    private fun getColorOfTile(tile: WireWorldTile): Color {
        return when (tile.type) {
            WireWorldTileType.TAIL -> Color.RED
            WireWorldTileType.CONDUCTOR -> Color.YELLOW
            WireWorldTileType.HEAD -> Color.BLUE
            else -> Color.BLACK
        }
    }

    private fun onClick(tile: WireWorldTile, e: MouseEvent) {
        tile.switch(
            if (e.button == MouseButton.PRIMARY)
                InputModeType.LEFT_CLICK_MODE
            else
                InputModeType.RIGHT_CLICK_MODE
        )
    }

    private fun drawField() {
        api.tileMatrix.forEach { tile ->
            wireWorldView.boxField.add(HBox().apply {
                add(
                    Rectangle(14.0, 14.0).apply {
                        fill = getColorOfTile(tile)
                        setOnMouseClicked { e ->
                            onClick(tile, e)
                            fill = getColorOfTile(tile)
                        }
                        tile.onChange = {
                            fill = getColorOfTile(tile)
                        }
                    }
                )
                paddingRight = 1.0
                paddingBottom = 1.0
            })
        }
    }

    override fun new(rowsString: String, colsString: String) {
        api.new(rowsString.toInt(), colsString.toInt()) { WireWorldTile() }
        drawField()
    }

    override fun load() {
        val file: File = getLoadedFile() ?: return
        api.loadFromFile(file) { WireWorldTile() }
        drawField()
    }

    override fun save() {
        val file: File = getSavedFile() ?: return
        api.saveToFile(file)
    }

    inner class Task : TimerTask() {
        override fun run() {
            api.cycleOnce()
        }
    }

    override fun play() {
        timer.cancel()
        timer.purge()
        timer = Timer()
        timer.schedule(Task(), 500, 2000)
    }

    override fun reset() {
        super.reset()
        api.reset()
    }
}

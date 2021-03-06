package com.triszt4n.wireworld.main

import com.triszt4n.wireworld.model.BoardApi
import com.triszt4n.wireworld.model.InputModeType
import com.triszt4n.wireworld.model.rulesets.WireWorldTile
import com.triszt4n.wireworld.model.rulesets.WireWorldTileType
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import tornadofx.*
import java.io.File
import java.util.*

class WireWorldController : AbstractController() {
    private val api: BoardApi<WireWorldTile> = BoardApi()

    private fun getColorOfTile(tile: WireWorldTile): Color {
        return when (tile.type) {
            WireWorldTileType.TAIL -> Color.ORANGERED
            WireWorldTileType.CONDUCTOR -> Color.GOLD
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

    override fun drawField(mainBox: VBox) {
        mainBox.replaceChildren(
            VBox().apply {
                for (y in 0 until api.tileMatrix.rows) {
                    hbox {
                        for (x in 0 until api.tileMatrix.cols) {
                            hbox {
                                rectangle {
                                    width = 14.0
                                    height = 14.0
                                    fill = getColorOfTile(api.tileMatrix[x, y])
                                    setOnMouseClicked { e ->
                                        onClick(api.tileMatrix[x, y], e)
                                        fill = getColorOfTile(api.tileMatrix[x, y])
                                    }
                                    api.tileMatrix[x, y].onChange = {
                                        fill = getColorOfTile(api.tileMatrix[x, y])
                                    }
                                }
                                paddingRight = 1.0
                                paddingBottom = 1.0
                            }
                        }
                    }
                }
            }
        )
    }

    override fun new(rowsString: String, colsString: String) {
        api.new(rowsString.toInt(), colsString.toInt()) { WireWorldTile() }
        onChange?.invoke()
    }

    override fun load() {
        super.load()
        val file: File = getLoadedFile() ?: return
        api.loadFromFile(file) { WireWorldTile() }
        onChange?.invoke()
    }

    override fun save() {
        super.save()
        val file: File = getSavedFile() ?: return
        api.saveToFile(file)
    }

    override fun play() {
        timer.cancel()
        timer.purge()
        timer = Timer()
        timer.schedule(Task { api.cycleOnce() }, 500, 200)
    }

    override fun reset() {
        super.reset()
        api.reset()
        onChange?.invoke()
    }
}

package com.triszt4n.wireworld.main

import com.triszt4n.wireworld.model.BoardApi
import com.triszt4n.wireworld.model.InputModeType
import com.triszt4n.wireworld.model.rulesets.GameOfLifeTile
import com.triszt4n.wireworld.model.rulesets.GameOfLifeTileType
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import tornadofx.*
import java.io.File
import java.util.*

class GameOfLifeController : AbstractController() {
    private val api: BoardApi<GameOfLifeTile> = BoardApi()

    private fun getColorOfTile(tile: GameOfLifeTile): Color {
        return when (tile.type) {
            GameOfLifeTileType.ALIVE -> Color.GOLD
            GameOfLifeTileType.DEAD -> Color.BLACK
        }
    }

    private fun onClick(tile: GameOfLifeTile) {
        // We don't care about which mouse button is clicked
        tile.switch(InputModeType.RIGHT_CLICK_MODE)
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
                                    setOnMouseClicked {
                                        onClick(api.tileMatrix[x, y])
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
        api.new(rowsString.toInt(), colsString.toInt()) { GameOfLifeTile() }
        onChange?.invoke()
    }

    override fun load() {
        super.load()
        val file: File = getLoadedFile() ?: return
        api.loadFromFile(file) { GameOfLifeTile() }
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
        timer.schedule(Task { api.cycleOnce() }, 500, 100)
    }

    override fun reset() {
        super.reset()
        api.reset()
        onChange?.invoke()
    }
}

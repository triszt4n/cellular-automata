package com.triszt4n.wireworld.model.rulesets

import com.triszt4n.wireworld.model.AbstractTile
import com.triszt4n.wireworld.model.InputModeType

enum class GameOfLifeTileType(val char: Char) {
    ALIVE('*'), DEAD(' ')
}

data class GameOfLifeTile(var type: GameOfLifeTileType = GameOfLifeTileType.DEAD) : AbstractTile() {
    private var originalType: GameOfLifeTileType = GameOfLifeTileType.DEAD
    private var newType: GameOfLifeTileType = GameOfLifeTileType.DEAD

    /**
     * View helper method for rectangles to react to changing types
     */
    lateinit var onChange: () -> Unit

    /**
     * @see AbstractTile.generateNewType
     */
    override fun generateNewType(neighbours: List<AbstractTile>) {
        val aliveCount = neighbours.filterIsInstance<GameOfLifeTile>().count {
            it.type == GameOfLifeTileType.ALIVE
        }

        newType = when (true) {
            type == GameOfLifeTileType.DEAD && aliveCount == 3 -> GameOfLifeTileType.ALIVE
            type == GameOfLifeTileType.ALIVE && aliveCount in 2..3 -> GameOfLifeTileType.ALIVE
            else -> GameOfLifeTileType.DEAD
        }
    }

    /**
     * @see AbstractTile.applyNewType
     */
    override fun applyNewType() {
        type = newType
        onChange()
    }

    /**
     * @see AbstractTile.switch
     */
    override fun switch(mode: InputModeType) {
        type = if (type == GameOfLifeTileType.ALIVE) GameOfLifeTileType.DEAD else GameOfLifeTileType.ALIVE
    }

    /**
     * @see AbstractTile.translateFromChar
     */
    override fun translateFromChar(char: Char) {
        type = if (char == GameOfLifeTileType.ALIVE.char) GameOfLifeTileType.ALIVE else GameOfLifeTileType.DEAD
        originalType = type
    }

    /**
     * @see AbstractTile.translateToChar
     */
    override fun translateToChar(): Char {
        return type.char
    }

    /**
     * @see AbstractTile.reset
     */
    override fun reset() {
        type = originalType
    }
}

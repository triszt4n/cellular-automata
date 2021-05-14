package com.triszt4n.wireworld.model.rulesets

import com.triszt4n.wireworld.model.AbstractTile
import com.triszt4n.wireworld.model.InputModeType

enum class GameOfLifeTileType(val char: Char) {
    ALIVE('*'), DEAD(' ')
}

data class GameOfLifeTile(var type: GameOfLifeTileType = GameOfLifeTileType.DEAD) : AbstractTile() {
    private val originalType: GameOfLifeTileType = type
    private var newType: GameOfLifeTileType = GameOfLifeTileType.DEAD

    override fun generateNewType(neighbours: List<AbstractTile>) {
        val aliveCount: Int = neighbours.count { (it as? GameOfLifeTile)?.type == GameOfLifeTileType.ALIVE }

        newType = when (true) {
            type == GameOfLifeTileType.DEAD && aliveCount == 3 -> GameOfLifeTileType.ALIVE
            type == GameOfLifeTileType.ALIVE && aliveCount in 2..3 -> GameOfLifeTileType.ALIVE
            else -> GameOfLifeTileType.DEAD
        }
    }

    override fun applyNewType() {
        type = newType
    }

    override fun switch(mode: InputModeType) {
        type = if (type == GameOfLifeTileType.ALIVE) GameOfLifeTileType.DEAD else GameOfLifeTileType.ALIVE
    }

    override fun translateFromChar(char: Char) {
        type = if (char == GameOfLifeTileType.ALIVE.char) GameOfLifeTileType.ALIVE else GameOfLifeTileType.DEAD
    }

    override fun translateToChar(): Char {
        return type.char
    }

    override fun reset() {
        type = originalType
    }
}

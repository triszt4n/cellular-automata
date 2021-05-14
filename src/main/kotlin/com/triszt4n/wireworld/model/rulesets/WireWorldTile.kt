package com.triszt4n.wireworld.model.rulesets

import com.triszt4n.wireworld.model.AbstractTile
import com.triszt4n.wireworld.model.InputModeType

enum class WireWorldTileType(val char: Char) {
    EMPTY(' '), HEAD('*'), TAIL('-'), CONDUCTOR('.')
}

data class WireWorldTile(var type: WireWorldTileType = WireWorldTileType.EMPTY) : AbstractTile() {
    private var newType: WireWorldTileType = WireWorldTileType.EMPTY

    /**
     * View helper method for rectangles to react to changing types
     */
    lateinit var onChange: () -> Unit

    /**
     * @see AbstractTile.generateNewType
     */
    override fun generateNewType(neighbours: List<AbstractTile>) {
        val headCount = neighbours.filterIsInstance<WireWorldTile>().count {
            it.type == WireWorldTileType.HEAD
        }

        newType = when (type) {
            WireWorldTileType.HEAD -> WireWorldTileType.TAIL
            WireWorldTileType.TAIL -> WireWorldTileType.CONDUCTOR
            WireWorldTileType.CONDUCTOR -> if (headCount in 1..2) WireWorldTileType.HEAD else WireWorldTileType.CONDUCTOR
            else -> WireWorldTileType.EMPTY
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
        type = when (mode) {
            InputModeType.LEFT_CLICK_MODE -> {
                if (type == WireWorldTileType.CONDUCTOR)
                    WireWorldTileType.EMPTY
                else
                    WireWorldTileType.CONDUCTOR
            }
            InputModeType.RIGHT_CLICK_MODE -> {
                if (type == WireWorldTileType.HEAD)
                    WireWorldTileType.TAIL
                else
                    WireWorldTileType.HEAD
            }
        }
    }

    /**
     * @see AbstractTile.translateFromChar
     */
    override fun translateFromChar(char: Char) {
        type = when (char) {
            WireWorldTileType.HEAD.char -> WireWorldTileType.HEAD
            WireWorldTileType.TAIL.char -> WireWorldTileType.TAIL
            WireWorldTileType.CONDUCTOR.char -> WireWorldTileType.CONDUCTOR
            else -> WireWorldTileType.EMPTY
        }
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
        type = when (type) {
            WireWorldTileType.EMPTY -> WireWorldTileType.EMPTY
            else -> WireWorldTileType.CONDUCTOR
        }
    }
}

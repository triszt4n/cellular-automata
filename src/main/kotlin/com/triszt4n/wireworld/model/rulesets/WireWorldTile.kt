package com.triszt4n.wireworld.model.rulesets

import com.triszt4n.wireworld.model.AbstractTile
import com.triszt4n.wireworld.model.InputModeType

enum class WireWorldTileType(val char: Char) {
    EMPTY(' '), HEAD('*'), TAIL('-'), CONDUCTOR('.')
}

data class WireWorldTile(var type: WireWorldTileType = WireWorldTileType.EMPTY) : AbstractTile() {
    private val originalType: WireWorldTileType = type
    private var newType: WireWorldTileType = WireWorldTileType.EMPTY
    lateinit var onChange: () -> Unit

    override fun generateNewType(neighbours: List<AbstractTile>) {
        val headCount: Int = neighbours.count { (it as? WireWorldTile)?.type == WireWorldTileType.HEAD }

        newType = when (type) {
            WireWorldTileType.HEAD -> WireWorldTileType.TAIL
            WireWorldTileType.TAIL -> WireWorldTileType.CONDUCTOR
            WireWorldTileType.CONDUCTOR -> if (headCount in 1..2) WireWorldTileType.HEAD else WireWorldTileType.CONDUCTOR
            else -> WireWorldTileType.EMPTY
        }
        println("$headCount ${newType.char} ")
    }

    override fun applyNewType() {
        type = newType
        onChange()
    }

    override fun switch(mode: InputModeType) {
        type = when (mode) {
            InputModeType.LEFT_CLICK_MODE -> {
                if (type == WireWorldTileType.EMPTY)
                    WireWorldTileType.CONDUCTOR
                else
                    WireWorldTileType.EMPTY
            }
            InputModeType.RIGHT_CLICK_MODE -> {
                WireWorldTileType.HEAD
            }
        }
    }

    override fun translateFromChar(char: Char) {
        type = when (char) {
            WireWorldTileType.HEAD.char -> WireWorldTileType.HEAD
            WireWorldTileType.TAIL.char -> WireWorldTileType.TAIL
            WireWorldTileType.CONDUCTOR.char -> WireWorldTileType.CONDUCTOR
            else -> WireWorldTileType.EMPTY
        }
    }

    override fun translateToChar(): Char {
        return type.char
    }

    override fun reset() {
        type = originalType
    }
}

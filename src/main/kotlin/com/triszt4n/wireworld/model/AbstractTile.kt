package com.triszt4n.wireworld.model

abstract class AbstractTile() {
    /**
     * This is the function that defines the ruleset
     */
    abstract fun generateNewType(neighbours: List<AbstractTile>)

    /**
     * This method should be fired after each generation to apply new type
     */
    abstract fun applyNewType()

    /**
     * This method handles right and left clicks
     */
    abstract fun switch(mode: InputModeType)

    /**
     * Sets the tile according to the give character
     */
    abstract fun translateFromChar(char: Char): AbstractTile
}

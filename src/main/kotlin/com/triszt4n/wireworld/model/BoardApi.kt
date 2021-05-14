package com.triszt4n.wireworld.model

import java.io.File

enum class InputModeType {
    LEFT_CLICK_MODE, RIGHT_CLICK_MODE
}

/**
 * The API, which can manage the board
 */
class BoardApi<T : AbstractTile> {
    lateinit var tileMatrix: Matrix<T>

    /**
     * Makes one new generation and applies it to tiles
     */
    fun cycleOnce() {
        tileMatrix.forEachIndexed { y, x, tile ->
            val neighbours: MutableList<T> = mutableListOf()

            for (i: Int in y-1..y+1) {
                for (j: Int in x-1..x+1) {
                    if (!(i == y && j == x)) {
                        try {
                            neighbours.add(tileMatrix[i, j])
                        }
                        catch (e: IllegalArgumentException) {
                        }
                    }
                }
            }

            tile.generateNewType(neighbours)
        }
        tileMatrix.forEach { it.applyNewType() }
    }

    /**
     * Creates new board from params
     */
    fun new(rows: Int, cols: Int, filler: () -> T) {
        require(rows <= 50 && cols <= 100 && rows > 0 && cols > 0)
        tileMatrix = createMatrix(cols, rows) { _, _ -> filler() }
    }

    /**
     * Loads a board from file, filling the matrix with given instances in filler lambda
     */
    fun loadFromFile(file: File, filler: () -> T) {
        val textLines: List<String> = file.readLines()
        val rows: Int = textLines[0].split(' ')[0].trim().toInt()
        val cols: Int = textLines[0].split(' ')[1].trim().toInt()
        new(rows, cols, filler)

        textLines.drop(1).forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                tileMatrix[x, y].translateFromChar(char)
            }
        }
    }

    /**
     * Save the board into file
     */
    fun saveToFile(file: File) {
        file.printWriter().use { printer ->
            printer.println("${tileMatrix.rows} ${tileMatrix.cols}")

            tileMatrix.forEachIndexed { x, _, tile ->
                printer.print(tile.translateToChar())
                if (x == tileMatrix.cols - 1) printer.println()
            }
        }
    }

    /**
     * Reset the board
     */
    fun reset() {
        tileMatrix.forEach { tile ->
            tile.reset()
        }
    }
}

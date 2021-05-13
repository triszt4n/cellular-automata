package com.triszt4n.wireworld.model

import java.io.File

enum class InputModeType {
    LEFT_CLICK_MODE, RIGHT_CLICK_MODE
}

class BoardApi<T : AbstractTile> {
    private lateinit var tileMatrix: MutableMatrix<T>

    fun cycleOnce() {
        tileMatrix.forEachIndexed { x, y, tile ->
            val neighbours: MutableList<T> = mutableListOf()

            for (i: Int in y-1..y+1)
                for (j: Int in x-1..x+1)
                    if (i != y && j != x) neighbours.add(tileMatrix[i, j])

            tile.generateNewType(neighbours)
        }
        tileMatrix.forEach { it.applyNewType() }
    }

    fun switchTileAt(x: Int, y: Int, mode: InputModeType) {
        tileMatrix[x, y].switch(mode)
    }

    fun new(rows: Int, cols: Int, filler: (Int, Int) -> T) {
        require(rows <= 30 && cols <= 60 && rows > 0 && cols > 0)
        tileMatrix = createMutableMatrix(cols, rows, filler)
    }

    fun loadFromFile(relativePath: String, filler: (Int, Int) -> T) {
        val textLines: List<String> = File(relativePath).readLines()
        val rows: Int = textLines[0].split(' ')[0].trim().toInt()
        val cols: Int = textLines[0].split(' ')[1].trim().toInt()
        new(rows, cols, filler)

        textLines.drop(1).forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                tileMatrix[x, y].translateFromChar(char)
            }
        }
    }

    fun saveToFile(relativePath: String) {
        File(relativePath).printWriter().use { printer ->
            printer.println("${tileMatrix.rows} ${tileMatrix.cols}")

            tileMatrix.forEachIndexed { x, _, tile ->
                printer.print(tile.translateToChar())
                if (x == tileMatrix.cols - 1) printer.println()
            }
        }
    }
}

package com.triszt4n.wireworld.model

import java.io.File

enum class InputModeType {
    LEFT_CLICK_MODE, RIGHT_CLICK_MODE
}

class CollectionApi<T : AbstractTile> {
    inner class Collection<T : AbstractTile>(rows: Int, cols: Int) {
        var tileMatrix: MutableMatrix<T> = mutableMatrixOf(cols, rows)
    }

    lateinit var collection: Collection<T>

    fun cycleOnce() {
        collection.tileMatrix.forEachIndexed { x, y, tile ->
            val neighbours: MutableList<T> = mutableListOf()

            for (i: Int in y-1..y+1)
                for (j: Int in x-1..x+1)
                    if (i != y && j != x) neighbours.add(collection.tileMatrix[i, j])

            tile.generateNewType(neighbours)
        }
        collection.tileMatrix.forEach { it.applyNewType() }
    }

    fun switchTileAt(x: Int, y: Int, mode: InputModeType) {
        collection.tileMatrix[x, y].switch(mode)
    }

    fun new(rows: Int, cols: Int) {
        require(rows <= 30 && cols <= 60 && rows > 0 && cols > 0)
        collection = Collection(rows, cols)
    }

    fun loadFromFile(relativePath: String) {
        val textLines: List<String> = File(relativePath).readText().split('\n')
        val rows: Int = textLines[0].split(' ')[0].toInt()
        val cols: Int = textLines[0].split(' ')[1].toInt()
        require(rows <= 30 && cols <= 60 && rows > 0 && cols > 0)

        val tileList: MutableList<T> = mutableListOf()
        textLines.drop(1).forEach { line ->
            tileList.addAll(line.map(::translateFromChar))
        }
        collection.tileMatrix = tileList.toMutableMatrix(cols, rows)
    }

    fun saveToFile(relativePath: String) {

    }
}

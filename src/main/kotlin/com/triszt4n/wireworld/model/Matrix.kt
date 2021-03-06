package com.triszt4n.wireworld.model

/**
 * From yinpeng's Kotlin-matrix implementation, only Matrix.kt
 * Modifications noted below in code
 *
 * Source: https://github.com/yinpeng/kotlin-matrix
 * Under Apache-2.0 license
 */

interface Matrix<out T> {
    val cols: Int
    val rows: Int

    operator fun get(x: Int, y: Int): T
}

val <T> Matrix<T>.size: Int
    get() = this.cols * this.rows

interface MutableMatrix<T>: Matrix<T> {
    operator fun set(x: Int, y: Int, value: T)
}

abstract class AbstractMatrix<out T>: Matrix<T> {
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append('[')
        forEachIndexed { x, y, value ->
            // === -> ==
            if (x == 0)
                sb.append('[')
            sb.append(value.toString())
            if (x == cols-1) {
                sb.append(']')
                if (y < rows-1)
                    sb.append(", ")
            } else {
                sb.append(", ")
            }
        }
        sb.append(']')
        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Matrix<*>) return false
        // !== -> !=
        if (rows != other.rows || cols != other.cols) return false

        var eq = true
        forEachIndexed { x, y, value ->
            if (value === null) {
                if (other[x, y] !== null) {
                    eq = false
                    return@forEachIndexed
                }
            } else {
                // !.equals() -> !=
                if (value != other[x, y]) {
                    eq = false
                    return@forEachIndexed
                }
            }
        }
        return eq
    }

    override fun hashCode(): Int {
        var h = 17
        h = h * 39 + cols
        h = h * 39 + rows
        forEach { h = h * 37 + (it?.hashCode() ?: 1) }
        return h
    }
}

/**
 * Removed TransposedMatrix, not needed
 */

internal open class ListMatrix<out T>(override val cols: Int, override val rows: Int, protected val list: List<T>) : AbstractMatrix<T>() {
    override operator fun get(x: Int, y: Int): T {
        // I needed this validation
        require(x >= 0 && y >= 0 && x < cols && y < rows)
        return list[y*cols+x]
    }
}

internal class MutableListMatrix<T>(cols: Int, rows: Int, list: MutableList<T>) : ListMatrix<T>(cols, rows, list), MutableMatrix<T> {
    override fun set(x: Int, y: Int, value: T) {
        // I needed this validation
        require(x >= 0 && y >= 0 && x < cols && y < rows)
        (list as MutableList<T>)[y*cols+x] = value
    }
}

fun <T> matrixOf(cols: Int, rows: Int, vararg elements: T): Matrix<T> {
    return ListMatrix(cols, rows, elements.asList())
}

fun <T> mutableMatrixOf(cols: Int, rows: Int, vararg elements: T): MutableMatrix<T> {
    return MutableListMatrix(cols, rows, elements.toMutableList())
}

// inline private -> private inline
private inline fun <T> prepareListForMatrix(cols: Int, rows: Int, init: (Int, Int) -> T): ArrayList<T> {
    val list = ArrayList<T>(cols * rows)
    // 0..x-1 -> 0 until x
    for (y in 0 until rows) {
        for (x in 0 until cols) {
            list.add(init(x, y))
        }
    }
    return list
}

@Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
inline fun <T> createMatrix(cols: Int, rows: Int, init: (Int, Int) -> T): Matrix<T> {
    return ListMatrix(cols, rows, prepareListForMatrix(cols, rows, init))
}

@Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
inline fun <T> createMutableMatrix(cols: Int, rows: Int, init: (Int, Int) -> T): MutableMatrix<T> {
    return MutableListMatrix(cols, rows, prepareListForMatrix(cols, rows, init))
}

inline fun <T, U> Matrix<T>.mapIndexed(transform: (Int, Int, T) -> U): Matrix<U> {
    return createMatrix(cols, rows) { x, y -> transform(x, y, this[x, y]) }
}

inline fun <T, U> Matrix<T>.map(transform: (T) -> U): Matrix<U> {
    // x, y -> _, _
    return mapIndexed { _, _, value -> transform(value) }
}

inline fun <T> Matrix<T>.forEachIndexed(action: (Int, Int, T) -> Unit) {
    // 0..x-1 -> 0 until x
    for (y in 0 until rows) {
        for (x in 0 until cols) {
            action(x, y, this[x, y])
        }
    }
}

inline fun <T> Matrix<T>.forEach(action: (T) -> Unit) {
    // x, y -> _, _
    forEachIndexed { _, _, value -> action(value) }
}

fun <T> Matrix<T>.toList(): List<T> {
    return prepareListForMatrix(cols, rows) { x, y -> this[x, y] }
}

fun <T> Matrix<T>.toMutableList(): MutableList<T> {
    return prepareListForMatrix(cols, rows) { x, y -> this[x, y] }
}

private fun <T> Iterable<T>.toArrayList(size: Int): ArrayList<T> {
    val list = ArrayList<T>(size)
    val itr = iterator()

    // 0..x-1 -> 0 until x
    for (i in 0 until size) {
        if (itr.hasNext()) {
            list.add(itr.next())
        } else {
            throw IllegalArgumentException("No enough elements")
        }
    }
    return list
}

fun <T> Iterable<T>.toMatrix(cols: Int, rows: Int): Matrix<T> {
    val list = toArrayList(cols * rows)
    return ListMatrix(cols, rows, list)
}

fun <T> Iterable<T>.toMutableMatrix(cols: Int, rows: Int): MutableMatrix<T> {
    val list = toArrayList(cols * rows)
    return MutableListMatrix(cols, rows, list)
}

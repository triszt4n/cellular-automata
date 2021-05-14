package com.triszt4n.wireworld.main

import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import tornadofx.Controller
import tornadofx.FileChooserMode
import tornadofx.chooseFile
import java.io.File
import java.util.*

abstract class AbstractController : Controller() {
    companion object {
        val txtFilter = FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt")
        val initialDir = File("configurations")
    }

    /**
     * Helper class for timer tasks
     */
    inner class Task(val task: () -> Unit) : TimerTask() {
        override fun run() {
            task()
        }
    }

    /**
     * View helper method to help it refresh the rectangle on the board
     */
    var onChange: (() -> Unit)? = null
    var timer: Timer = Timer()

    /**
     * Draws the rectangle representation into a VBox
     */
    abstract fun drawField(mainBox: VBox)

    /**
     * Starts generation, developer's responsibility
     */
    abstract fun play()

    /**
     * Pauses generation
     */
    open fun pause() {
        timer.cancel()
        timer.purge()
    }

    /**
     * Resets board
     */
    open fun reset() {
        timer.cancel()
        timer.purge()
    }

    /**
     * New board option
     */
    abstract fun new(rowsString: String, colsString: String)

    /**
     * Helper method to get the file from the file load dialog
     */
    fun getLoadedFile(): File? {
        val files = chooseFile(filters = arrayOf(txtFilter), initialDirectory = initialDir)
        return if (files.isNotEmpty()) files[0] else null
    }

    /**
     * Helper method to get the file from the file save dialog
     */
    fun getSavedFile(): File? {
        val files = chooseFile(filters = arrayOf(txtFilter), mode = FileChooserMode.Save, initialDirectory = initialDir)
        return if (files.isNotEmpty()) files[0] else null
    }

    /**
     * Initiates loading from file
     */
    open fun load() {
        pause()
    }

    /**
     * Initiates saving into file
     */
    open fun save() {
        pause()
    }
}
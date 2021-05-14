package com.triszt4n.wireworld.main

import javafx.stage.FileChooser
import tornadofx.Controller
import tornadofx.FileChooserMode
import tornadofx.chooseFile
import java.io.File

abstract class AbstractController : Controller() {
    companion object {
        val txtFilter = FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt")
        val initialDir = File("configurations")
    }

    fun play() {

    }

    fun pause() {

    }

    fun reset() {

    }

    abstract fun new(rowsString: String, colsString: String)

    fun getLoadedFile(): File? {
        val files = chooseFile(filters = arrayOf(txtFilter), initialDirectory = initialDir)
        return if (files.isNotEmpty()) files[0] else null
    }

    fun getSavedFile(): File? {
        val files = chooseFile(filters = arrayOf(txtFilter), mode = FileChooserMode.Save, initialDirectory = initialDir)
        return if (files.isNotEmpty()) files[0] else null
    }

    abstract fun load()

    abstract fun save()
}
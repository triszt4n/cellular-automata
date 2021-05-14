package com.triszt4n.wireworld.main

import com.triszt4n.wireworld.Styles
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.TextField
import tornadofx.*

class NewBoardView: View("New Board") {
    var rowsField: TextField by singleAssign()
    var colsField: TextField by singleAssign()

    private val model = object : ViewModel() {
        val alert = SimpleStringProperty()
    }

    lateinit var controller: AbstractController

    override val root = vbox {
        addClass(Styles.basicPadding)
        hbox {
            vbox {
                label("Rows: ") {
                    addClass(Styles.inputLabel)
                }
                label("Columns: ") {
                    addClass(Styles.inputLabel)
                }
            }
            vbox {
                rowsField = textfield() {
                    addClass(Styles.inputLabel)
                }
                colsField = textfield() {
                    addClass(Styles.inputLabel)
                }
            }
        }
        text {
            bind(model.alert)
            addClass(Styles.inputLabel)
        }
        button("OK") {
            addClass(Styles.inputLabel)
            alignment = Pos.BASELINE_RIGHT
            action {
                if (rowsField.text.isNotBlank() && colsField.text.isNotBlank())
                    controller.new(rowsField.text, colsField.text)
                close()
            }
        }
    }
}

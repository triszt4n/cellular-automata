package com.triszt4n.wireworld.main

import com.triszt4n.wireworld.Styles
import javafx.beans.property.SimpleIntegerProperty
import javafx.util.converter.NumberStringConverter
import tornadofx.*

class MainView: View() {
    private val controller: MainController by inject()

    private val model = object : ViewModel() {
        val counter = SimpleIntegerProperty()
    }

    override val root = vbox {
        label(title) {
            addClass(Styles.heading)
        }
        text {
            bind(model.counter, converter = NumberStringConverter())
        }
        hbox {
            button("Click") {
                setOnAction {
                    model.counter.value += 1
                }
            }
            button("Reset") {
                setOnAction {
                    model.counter.value = 0
                }
            }
        }
    }
}

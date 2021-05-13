package com.triszt4n.wireworld.main

import com.triszt4n.wireworld.Styles
import javafx.scene.shape.FillRule
import tornadofx.*

class MainView: View("WireWorld") {
    companion object {
        const val playSvg = "M10 18a8 8 0 100-16 8 8 0 000 16zM9.555 7.168A1 1 0 008 8v4a1 1 0 001.555.832l3-2a1 1 0 000-1.664l-3-2z"
        const val pauseSvg = "M18 10a8 8 0 11-16 0 8 8 0 0116 0zM7 8a1 1 0 012 0v4a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v4a1 1 0 102 0V8a1 1 0 00-1-1z"
        const val resetSvg = "M10 18a8 8 0 100-16 8 8 0 000 16z M8 7a1 1 0 00-1 1v4a1 1 0 001 1h4a1 1 0 001-1V8a1 1 0 00-1-1H8z"
    }

    private val controller: MainController by inject()

    override val root = vbox {
        menubar {
            menu("File") {
                item("New") {
                    action {

                    }
                }
                item("Load from...") {
                    action {

                    }
                }
                item("Save as...") {
                    action {

                    }
                }
            }
        }
        hbox {
            hbox {
                addClass(Styles.buttons)
                button {
                    svgpath(playSvg, FillRule.EVEN_ODD)
                    action {
                        controller.play()
                    }
                }
                button {
                    svgpath(pauseSvg, FillRule.EVEN_ODD)
                    action {
                        controller.pause()
                    }
                }
                button {
                    svgpath(resetSvg, FillRule.EVEN_ODD)
                    action {
                        controller.reset()
                    }
                }
            }
            label(title) {
                addClass(Styles.heading)
            }
        }
        vbox {

        }
    }
}

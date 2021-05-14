package com.triszt4n.wireworld.main

import com.triszt4n.wireworld.Styles
import javafx.scene.paint.Color
import javafx.scene.shape.FillRule
import javafx.stage.StageStyle
import tornadofx.*

abstract class AbstractView(title: String): View(title) {
    companion object {
        const val playSvg = "M10 18a8 8 0 100-16 8 8 0 000 16zM9.555 7.168A1 1 0 008 8v4a1 1 0 001.555.832l3-2a1 1 0 000-1.664l-3-2z"
        const val pauseSvg = "M18 10a8 8 0 11-16 0 8 8 0 0116 0zM7 8a1 1 0 012 0v4a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v4a1 1 0 102 0V8a1 1 0 00-1-1z"
        const val resetSvg = "M10 18a8 8 0 100-16 8 8 0 000 16z M8 7a1 1 0 00-1 1v4a1 1 0 001 1h4a1 1 0 001-1V8a1 1 0 00-1-1H8z"
    }

    lateinit var controller: AbstractController

    override val root = vbox {
        setMinSize(720.0, 360.0)
        menubar {
            menu("File") {
                item("New") {
                    action {
                        val view: NewBoardView = find()
                        view.controller = controller
                        view.openModal(stageStyle = StageStyle.UTILITY)
                    }
                }
                item("Load from...") {
                    action {
                        controller.load()
                    }
                }
                item("Save as...") {
                    action {
                        controller.save()
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
                    shortcut("Ctrl+A")
                }
                button {
                    svgpath(pauseSvg, FillRule.EVEN_ODD)
                    action {
                        controller.pause()
                    }
                    shortcut("Ctrl+S")
                }
                button {
                    svgpath(resetSvg, FillRule.EVEN_ODD)
                    action {
                        controller.reset()
                    }
                    shortcut("Ctrl+D")
                }
            }
            label(title) {
                addClass(Styles.heading)
            }
        }
        vbox {
            addClass(Styles.basicPadding)
            hbox {
                rectangle {
                    width = 14.0
                    height = 14.0
                    fill = Color.FORESTGREEN
                }
                paddingBottom = 1.0
                paddingRight = 1.0
            }
        }
    }
}

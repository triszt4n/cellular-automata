package com.triszt4n.wireworld.main

import com.triszt4n.wireworld.Styles
import tornadofx.*

class NewBoardView: View("New Board") {
    override val root = vbox {
        label(title) {
            addClass(Styles.heading)
        }
        hbox {
            button()
        }
    }
}

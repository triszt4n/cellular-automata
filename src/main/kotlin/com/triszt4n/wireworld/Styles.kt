package com.triszt4n.wireworld

import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val buttons by cssclass()
        val basicPadding by cssclass()
        val inputLabel by cssclass()
    }

    init {
        label and heading {
            padding = box(10.px, 30.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
        buttons {
            padding = box(12.px, 20.px)
        }
        basicPadding {
            padding = box(30.px)
        }
        inputLabel {
            padding = box(6.px)
            fontSize = 14.px
        }
    }
}

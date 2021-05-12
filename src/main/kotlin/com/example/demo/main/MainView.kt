package com.example.demo.main

import tornadofx.*

class MainView: View() {
    override val root = vbox {
        button("Press me")
        label("Waiting")
    }
}

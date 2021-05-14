package com.triszt4n.wireworld

import com.triszt4n.wireworld.main.GameOfLifeView
import com.triszt4n.wireworld.main.WireWorldView
import tornadofx.App

class MyApp : App(WireWorldView::class, Styles::class)
//class MyApp : App(GameOfLifeView::class, Styles::class)

package com.ownedoutcomes.view

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import ktx.scene2d.*

class Game(stage: Stage) : AbstractView(stage) {
    override val root = table {
        setFillParent(true)
        table { cell ->
            cell.expand().align(Align.bottom)
            image("play-button")
        }
    }
}

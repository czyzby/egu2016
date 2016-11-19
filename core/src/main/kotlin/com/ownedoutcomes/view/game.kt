package com.ownedoutcomes.view

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import com.ownedoutcomes.logic.GameController
import ktx.scene2d.*

class Game(stage: Stage, private val gameController: GameController) : AbstractView(stage) {
    override val root = table {
        setFillParent(true)
        table { cell ->
            cell.expand().align(Align.bottom)
            image("play-button")
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        gameController.resize(width, height)
    }

    override fun render(delta: Float) {
        gameController.update(delta)
        super.render(delta)
    }
}

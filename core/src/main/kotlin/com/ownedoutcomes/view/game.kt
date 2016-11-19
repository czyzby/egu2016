package com.ownedoutcomes.view

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.ownedoutcomes.logic.GameController
import ktx.actors.onKey
import ktx.scene2d.image
import ktx.scene2d.table

class Game(stage: Stage, private val gameController: GameController) : AbstractView(stage) {
    lateinit var image: Actor

    override val root = table {
        setFillParent(true)
        table { cell ->
            cell.expand().align(Align.bottom)
            image = image("play-button") {
                onKey { input: InputEvent, image: Image, c: Char ->
                    run {
                        if (c == ' ')
                            gameController.players.forEach { it.enlarge() }
                        if (c == '1')
                            gameController.players.forEach { it.smaller() }
                    }
                }
            }
        }

    }

    override fun show() {
        super.show()
        stage.keyboardFocus = image
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

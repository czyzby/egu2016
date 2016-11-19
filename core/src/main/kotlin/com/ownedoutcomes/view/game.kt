package com.ownedoutcomes.view

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import com.ownedoutcomes.logic.GameController
import com.ownedoutcomes.logic.GameRenderer
import ktx.actors.onKey
import ktx.scene2d.KTableWidget
import ktx.scene2d.table

class Game(stage: Stage, private val gameController: GameController, val gameRenderer: GameRenderer) : AbstractView(stage) {
    override val root = table {
        setFillParent(true)
        table { cell ->
            cell.expand().align(Align.bottom)
        }

        onKey { inputEvent: InputEvent, kTableWidget: KTableWidget, c: Char ->
            run {
                if (c == ' ')
                    gameController.players.forEach { it.enlarge() }
                if (c == '1')
                    gameController.players.forEach { it.smaller() }
            }
        }
    }

    override fun show() {
        super.show()
        stage.keyboardFocus = root
    }

    override fun hide() {
        super.hide()
        gameController.destroy()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        gameController.resize(width, height)
    }

    override fun render(delta: Float) {
        gameController.update(delta)
        gameRenderer.render(delta)
        super.render(delta)
    }
}

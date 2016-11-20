package com.ownedoutcomes.view

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.ownedoutcomes.logic.GameController
import com.ownedoutcomes.logic.GameRenderer
import com.ownedoutcomes.logic.currentGameLevel
import com.ownedoutcomes.logic.currentGamePoints
import ktx.actors.onKey
import ktx.scene2d.KTableWidget
import ktx.scene2d.label
import ktx.scene2d.table

class Game(stage: Stage, private val gameController: GameController, val gameRenderer: GameRenderer) : AbstractView(stage) {
    private lateinit var pointsLabel: Label

    public lateinit var bgActor: Image

    override val root = table {
        setFillParent(true)
        table { cell ->
            cell.expand().align(Align.bottom)
        }
        pointsLabel = label(style = "points", text = "0") {
            cell -> cell.align(Align.topRight)
        }

        onKey { inputEvent: InputEvent, kTableWidget: KTableWidget, c: Char ->
            run {
                if (c == ' ')
                    gameController.players.forEach { it.enlarge() }
                if (c == '1')
                    gameController.players.forEach { it.smaller() }
                if (c == 'p')
                    (gameController.gameViewport.camera as OrthographicCamera).zoom += .1f
                if (c == 'l')
                    (gameController.gameViewport.camera as OrthographicCamera).zoom -= .1f
                if (c == 'd')
                    gameController.playersToAdd += 10
            }
        }
    }

    override fun show() {
        bgActor = Image(root.skin.getDrawable("background${currentGameLevel % 4}"))
        stage.addActor(bgActor)
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
        super.render(delta)
        pointsLabel.setText("Level: $currentGameLevel Points: $currentGamePoints")
        gameRenderer.render(delta)
    }
}

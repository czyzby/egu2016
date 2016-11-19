package com.ownedoutcomes.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ownedoutcomes.Runner
import com.ownedoutcomes.logic.GameController
import ktx.actors.onChange
import ktx.actors.onClick
import ktx.inject.inject
import ktx.scene2d.image
import ktx.scene2d.table
import ktx.scene2d.textButton

class Menu(stage: Stage) : AbstractView(stage) {
    override val root = table {
        setFillParent(true)
        image("title")
        row()
        textButton(text = "Play!", style = "play-button") {
            color = Color.PINK
            onChange { event, button ->
                inject<GameController>().reload()
                inject<Runner>().setCurrentView(inject<Game>())
            }
        }
    }
}

class GameOver(stage: Stage) : AbstractView(stage) {
    override val root = table {
        setFillParent(true)
        textButton(text = "Play again!", style = "game-over-button") {
            color = Color.PINK
            onClick { event, button ->
                inject<GameController>().reload()
                inject<Runner>().setCurrentView(inject<Game>())
            }
        }
    }
}
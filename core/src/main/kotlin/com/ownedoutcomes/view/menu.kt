package com.ownedoutcomes.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ownedoutcomes.Runner
import ktx.actors.onChange
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
                inject<Runner>().setCurrentView(inject<Game>())
            }
        }
    }
}
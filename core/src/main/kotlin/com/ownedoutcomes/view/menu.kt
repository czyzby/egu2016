package com.ownedoutcomes.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.ownedoutcomes.Runner
import com.ownedoutcomes.logic.GameController
import ktx.actors.onChange
import ktx.inject.inject
import ktx.scene2d.button
import ktx.scene2d.image
import ktx.scene2d.table

class Menu(stage: Stage) : AbstractView(stage) {
    override val root = table {
        setFillParent(true)
        image("title")
        row()
        button("play") {
            color = Color.PINK
            onChange { event, button ->
                inject<GameController>().reload()
                inject<Runner>().setCurrentView(inject<Game>())
            }
        }
    }
}
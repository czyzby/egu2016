package com.ownedoutcomes.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.scene2d.*

class Menu(stage: Stage) : AbstractView(stage) {
    val root = table {
        setFillParent(true)
        image("title")
        row()
        button("play") {
            color = Color.PINK
        }

    }

    override fun show() {
        stage.addActor(root)
    }
}
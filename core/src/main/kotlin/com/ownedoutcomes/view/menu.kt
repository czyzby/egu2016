package com.ownedoutcomes.view

import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.scene2d.*

class Menu(stage: Stage) : AbstractView(stage) {
    val root = table {
        setFillParent(true)
        image("title")
    }

    override fun show() {
        stage.addActor(root)
    }
}
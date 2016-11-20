package com.ownedoutcomes.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.ownedoutcomes.Runner
import com.ownedoutcomes.logic.GameController
import ktx.actors.onChange
import ktx.actors.onClick
import ktx.assets.getValue
import ktx.assets.loadOnDemand
import ktx.inject.inject
import ktx.scene2d.image
import ktx.scene2d.table
import ktx.scene2d.textButton
import ktx.scene2d.textField

class Menu(stage: Stage) : AbstractView(stage) {
    val backgroundImage by loadOnDemand<Texture>(path = "menu-background.png")
    override val root = table {
        setFillParent(true)
        backgroundImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        background = TextureRegionDrawable(TextureRegion(backgroundImage, 0, 0, 1000, 750))
        image("title")
        row()
        textButton(text = "Play!", style = "play-button") {
            color = Color.FIREBRICK
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
        textField(text = "GAME OVER!", style = "game-over") {
            color = Color.FIREBRICK
        }
        row()
        textButton(text = "Play again!", style = "game-over-button") {
            color = Color.FIREBRICK
            onClick { event, button ->
                inject<GameController>().reload()
                inject<Runner>().setCurrentView(inject<Game>())
            }
        }
    }
}
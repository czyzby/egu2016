package com.ownedoutcomes.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.ownedoutcomes.Runner
import com.ownedoutcomes.logic.GameController
import ktx.actors.onChange
import ktx.actors.onClick
import ktx.assets.getValue
import ktx.assets.loadOnDemand
import ktx.inject.inject
import ktx.scene2d.*

class Menu(stage: Stage) : AbstractView(stage) {
    val backgroundImage by loadOnDemand<Texture>(path = "menu-background.png")
    override val root = table {
        setFillParent(true)
        backgroundImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        background = TextureRegionDrawable(TextureRegion(backgroundImage, 0, 0, 1000, 750))
        button(style = "start") { cell ->
            onChange { event, button ->
                inject<GameController>().reload()
                inject<Runner>().setCurrentView(inject<Game>())
            }
            cell.expand().pad(100f).align(Align.bottomLeft)
        }
    }
}

class GameOver(stage: Stage) : AbstractView(stage) {
    override val root = table {
        background = skin.getDrawable("background0")
        setFillParent(true)
        label(text = "GAME OVER!", style = "game-over") {
            color = Color.FIREBRICK
        }
        row()
        textButton(text = "Play again!", style = "game-over-button") { cell ->
            pad(25f)
            color = Color.FIREBRICK
            onClick { event, button ->
                inject<GameController>().reload()
                inject<Runner>().setCurrentView(inject<Game>())
            }
            cell.padTop(15f)
        }
    }
}

class NextLevel(stage: Stage) : AbstractView(stage) {
    override val root = table {
        background = skin.getDrawable("background0")
        setFillParent(true)
        label(text = "Next level!", style = "game-over") {
            color = Color.FIREBRICK
        }
        row()
        textButton(text = "Go to next level!", style = "game-over-button") { cell ->
            pad(25f)
            color = Color.FIREBRICK
            onClick { event, button ->
                inject<GameController>().reload()
                inject<Runner>().setCurrentView(inject<Game>())
            }
            cell.padTop(15f)
        }
    }
}
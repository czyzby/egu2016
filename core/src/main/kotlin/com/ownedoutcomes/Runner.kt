package com.ownedoutcomes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.viewport.Viewport
import com.ownedoutcomes.asset.loadSkin
import com.ownedoutcomes.view.Game
import com.ownedoutcomes.view.Menu
import com.ownedoutcomes.view.MockView
import com.ownedoutcomes.view.View
import ktx.app.KotlinApplication
import ktx.app.LetterboxingViewport
import ktx.inject.inject
import ktx.inject.register
import ktx.scene2d.Scene2DSkin

class Runner : KotlinApplication() {
    private var view: View = MockView()

    override fun create() {
        val viewport: Viewport = LetterboxingViewport(targetPpiX = 96f, targetPpiY = 96f, aspectRatio = 4f / 3f)
        val batch = SpriteBatch()
        val stage = Stage(viewport, batch)
        val skin = loadSkin()
        Scene2DSkin.defaultSkin = skin
        val menuView = Menu(stage)
        val gameView = Game(stage)
        val runner = this
        view = menuView
        register {
            bindSingleton(runner)
            bindSingleton(skin)
            bindSingleton(batch)
            bindSingleton(stage)
            bindSingleton(menuView)
            bindSingleton(gameView)
        }
        Gdx.input.inputProcessor = stage
        stage.addAction(Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.5f)))
        view.show()
    }

    override fun resize(width: Int, height: Int) {
        view.resize(width, height)
    }

    override fun render(delta: Float) {
        view.render(delta)
    }

    fun setCurrentView(nextView: View) {
        inject<Stage>().addAction(Actions.sequence(
                Actions.fadeOut(0.5f),
                Actions.run {
                    view.hide()
                    view = nextView
                    nextView.show()
                },
                Actions.fadeIn(0.5f),
                Actions.alpha(1f)
        ))
    }
}
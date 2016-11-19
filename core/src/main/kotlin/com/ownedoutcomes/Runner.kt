package com.ownedoutcomes

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import com.ownedoutcomes.asset.loadSkin
import com.ownedoutcomes.view.Menu
import com.ownedoutcomes.view.MockView
import com.ownedoutcomes.view.View
import ktx.app.KotlinApplication
import ktx.app.LetterboxingViewport
import ktx.inject.register
import ktx.scene2d.Scene2DSkin

class Runner : KotlinApplication() {
    var view: View = MockView()

    override fun create() {
        val viewport: Viewport = LetterboxingViewport(targetPpiX = 96f, targetPpiY = 96f, aspectRatio = 4f / 3f)
        val batch = SpriteBatch()
        val stage = Stage(viewport, batch)
        val skin = loadSkin()
        register {
            bindSingleton(batch)
            bindSingleton(stage)
            bindSingleton(skin)
        }
        Scene2DSkin.defaultSkin = skin
        view = Menu(stage)
        view.show()
    }

    override fun resize(width: Int, height: Int) {
        view.resize(width, height)
    }

    override fun render(delta: Float) {
        view.render(delta)
    }
}
package com.ownedoutcomes.view

import com.badlogic.gdx.scenes.scene2d.Stage

interface View {
    fun show()
    fun resize(width: Int, height: Int)
    fun render(delta: Float)
    fun hide()
}

abstract class AbstractView(val stage: Stage) : View {
    override fun show() {
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        stage.act(delta)
        stage.draw();
    }

    override fun hide() {
    }
}

class MockView() : View {
    override fun show() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resize(width: Int, height: Int) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render(delta: Float) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hide() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
package com.ownedoutcomes.logic

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.math.vec2

class InputController(val viewport: Viewport) {
    private val pointer = vec2()
    val x: Float
        get() = pointer.x
    val y: Float
        get() = pointer.y

    fun update() {
        pointer.x = Gdx.input.x.toFloat()
        pointer.y = Gdx.input.y.toFloat()
        viewport.unproject(pointer)
    }
}
package com.ownedoutcomes.logic

import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.ownedoutcomes.logic.entity.Player
import ktx.math.vec2

val gameWorldWidth = 8f
val gameWorldHeight = 6f

class GameController {
    private val gameViewport: Viewport = FitViewport(gameWorldWidth, gameWorldHeight)
    private val renderer = Box2DDebugRenderer()
    private lateinit var world: World
    fun reload() {
        world = World(vec2(0f, 0f), true)
        addBodies()
    }

    private fun addBodies() {
        Player(world)
    }

    fun resize(width: Int, height: Int) {
        gameViewport.update(width, height)
    }

    fun update(delta: Float) {
        world.step(delta, 8, 3)
        // TODO remove destroyed bodies
        renderer.render(world, gameViewport.camera.combined)
    }
}
package com.ownedoutcomes.logic

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.ownedoutcomes.logic.entity.Food
import com.ownedoutcomes.logic.entity.Player
import ktx.collections.gdxArrayOf
import ktx.math.vec2

val gameWorldWidth = 8f
val gameWorldHeight = 6f
val halfGameWorldWidth = gameWorldWidth / 2f
val halfGameWorldHeight = gameWorldHeight / 2f

class GameController {
    private val gameViewport: Viewport = FitViewport(gameWorldWidth, gameWorldHeight)
    private val renderer = Box2DDebugRenderer()
    private val inputController = InputController(gameViewport)
    private lateinit var world: World
    val players = gdxArrayOf<Player>()
    private val food = gdxArrayOf<Food>()
    private var timeSinceSpawn = 100f

    fun reload() {
        world = World(vec2(0f, 0f), true)
        addBodies()
    }

    private fun addBodies() {
        players.add(Player(world, inputController))
    }

    fun resize(width: Int, height: Int) {
        gameViewport.update(width, height)
    }

    fun update(delta: Float) {
        timeSinceSpawn += delta
        if (timeSinceSpawn > MathUtils.random(2f, 4f)) {
            food.add(Food(world))
            timeSinceSpawn = 0f
        }
        inputController.update()
        world.step(delta, 8, 3)
        players.forEach { it.update(delta) }
        food.forEach { it.update(delta) }
        // TODO remove destroyed bodies
        // TODO add world bounds
        // TODO remove enemies that touch world bounds
        renderer.render(world, gameViewport.camera.combined)
    }
}
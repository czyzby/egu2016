package com.ownedoutcomes.logic

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.ownedoutcomes.Runner
import com.ownedoutcomes.logic.entity.Food
import com.ownedoutcomes.logic.entity.Player
import com.ownedoutcomes.logic.entity.Shoe
import com.ownedoutcomes.view.GameOver
import ktx.collections.gdxArrayOf
import ktx.collections.gdxSetOf
import ktx.collections.isEmpty
import ktx.collections.isNotEmpty
import ktx.inject.inject
import ktx.math.vec2

val gameWorldWidth = 8f
val gameWorldHeight = 6f
val halfGameWorldWidth = gameWorldWidth / 2f
val halfGameWorldHeight = gameWorldHeight / 2f

class GameController {
    val gameViewport: Viewport = FitViewport(gameWorldWidth, gameWorldHeight)
    private val renderer = Box2DDebugRenderer()
    private val inputController = InputController(gameViewport)
    private lateinit var world: World
    val players = gdxSetOf<Player>()
    val food = gdxSetOf<Food>()
    val shoes = gdxSetOf<Shoe>()
    val playersToRemove = gdxArrayOf<Player>()
    val foodToRemove = gdxArrayOf<Food>()
    val shoesToRemove = gdxArrayOf<Shoe>()
    private var timeSinceSpawn = 100f
    private var timeSincePlayerSpawn = 0f
    private var timeSinceShoeSpawn = 0f

    fun reload() {
        world = World(vec2(0f, 0f), true)
        world.setContactListener(ContactController(this))
        addBodies()
    }

    private fun addBodies() {
        players.add(Player(world, inputController).initiate())
    }

    fun resize(width: Int, height: Int) {
        gameViewport.update(width, height)
    }

    fun update(delta: Float) {
        spawnPlayers(delta)
        spawnFood(delta)
        spawnShoe(delta)
        inputController.update()
        world.step(delta, 8, 3)
        players.forEach { it.update(delta) }
        food.forEach { it.update(delta) }
        shoes.forEach { it.update(delta) }
        removeFood()
        removePlayers()
        removeShoes()
        // TODO add world bounds
        // TODO remove enemies that touch world bounds
        renderer.render(world, gameViewport.camera.combined)
    }

    private fun  spawnPlayers(delta: Float) {
        timeSincePlayerSpawn + delta
        if(timeSincePlayerSpawn > MathUtils.random(60f, 120f)) {

        }
    }

    private fun spawnFood(delta: Float) {
        timeSinceSpawn += delta
        if (timeSinceSpawn > MathUtils.random(1f, 2f)) {
            food.add(Food(world).initiate())
            timeSinceSpawn = 0f
        }
    }

    private fun spawnShoe(delta: Float) {
        timeSinceShoeSpawn += delta
        if (timeSinceShoeSpawn > MathUtils.random(1f, 2f)) {
            shoes.add(Shoe(world).initiate())
            timeSinceShoeSpawn = 0f
        }
    }

    private fun removePlayers() {
        if (playersToRemove.isNotEmpty()) {
            playersToRemove.forEach {
                world.destroyBody(it.body)
                players.remove(it)
            }
            playersToRemove.clear()
            if(players.isEmpty()) {
                inject<Runner>().setCurrentView(inject<GameOver>())
            }
        }
    }

    private fun removeFood() {
        if (foodToRemove.isNotEmpty()) {
            foodToRemove.forEach {
                world.destroyBody(it.body)
                food.remove(it)
            }
            foodToRemove.clear()
        }
    }

    private fun removeShoes() {
        if (shoesToRemove.isNotEmpty()) {
            shoesToRemove.forEach {
                world.destroyBody(it.body)
                shoes.remove(it)
            }
            shoesToRemove.clear()
        }
    }

    fun destroy() {
        world.dispose()
        players.clear()
        playersToRemove.clear()
        food.clear()
        foodToRemove.clear()
        shoes.clear()
        shoesToRemove.clear()
        timeSinceSpawn = 100f
        timeSincePlayerSpawn = 0f
    }
}
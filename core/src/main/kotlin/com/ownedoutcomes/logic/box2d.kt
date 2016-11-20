package com.ownedoutcomes.logic

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.ownedoutcomes.Runner
import com.ownedoutcomes.logic.entity.*
import com.ownedoutcomes.view.GameOver
import ktx.collections.gdxSetOf
import ktx.collections.isEmpty
import ktx.collections.isNotEmpty
import ktx.inject.inject
import ktx.math.vec2
import java.util.concurrent.ThreadLocalRandom


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
    val boosters = gdxSetOf<FoodBooster>()

    var playersToAdd = 0

    val foodToReduce = gdxSetOf<Pair<Food, Float>>()
    val whatToEat = gdxSetOf<Pair<AbstractEntity, AbstractEntity>>()

    val attackingPlayers = gdxSetOf<Player>()

    val playersToRemove = gdxSetOf<Player>()
    val foodToRemove = gdxSetOf<Food>()
    val shoesToRemove = gdxSetOf<Shoe>()

    val boostersToRemove = gdxSetOf<FoodBooster>()

    private var timeSinceSpawn = 100f
    private var timeSincePlayerSpawn = 0f
    private var timeSinceShoeSpawn = 0f
    private var timeSinceBoostSpawn = 0f

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

        inputController.update()
        world.step(delta, 8, 3)

        runEeaters()
        runFoodReduction()

        spawnFood(delta)
        spawnShoe(delta)

        spawnNewPlayers(delta)
        spawnBoosters(delta)


        players.forEach { it.update(delta) }
        food.forEach { it.update(delta) }
        shoes.forEach { it.update(delta) }
        boosters.forEach { it.update(delta) }

        removeFood()
        removePlayers()
        removeShoes()
        removeBoosters()

        // TODO add world bounds
        // TODO remove enemies that touch world bounds
        renderer.render(world, gameViewport.camera.combined)
    }

    private fun runEeaters() {
        whatToEat.forEach {
            pair ->
            run {
                pair.first.eat(pair.second)
            }

        }
        whatToEat.clear()
    }

    private fun runFoodReduction() {
        foodToReduce.forEach {
            pair ->
            run {
                pair.first.reduce(pair.second)
            }

        }
        foodToReduce.clear()
    }

    private fun spawnFood(delta: Float) {
        timeSinceSpawn += delta
        if (timeSinceSpawn > random(1f, 2f)) {
            food.add(Food(world).initiate())
            timeSinceSpawn = 0f
        }
    }

    private fun spawnShoe(delta: Float) {
        timeSinceShoeSpawn += delta
        if (timeSinceShoeSpawn > random(10f, 20f)) {
            shoes.add(Shoe(world).initiate())
            timeSinceShoeSpawn = 0f
        }
    }

    private fun spawnBoosters(delta: Float) {
        timeSinceBoostSpawn += delta
        if (timeSinceBoostSpawn > random(1f, 2f)) {
            boosters.add(FoodBooster(world).initiate())
            timeSinceBoostSpawn = 0f
        }
    }

    private fun spawnNewPlayers(delta: Float) {
        if (playersToAdd > 0) {
            while(playersToAdd-- > 0) {
                addBodies()
            }
            playersToAdd = 0
        }
    }

    private fun removePlayers() {
        if (playersToRemove.isNotEmpty()) {
            playersToRemove.forEach {
                val bd = Array<Body>()
                world.getBodies(bd);

                if (bd.contains(it.body, true)) {
                    it.body.isActive = false;
                    world.destroyBody(it.body)
                }

                players.remove(it)
            }
            playersToRemove.clear()
            if (players.isEmpty()) {
                inject<Runner>().setCurrentView(inject<GameOver>())
            }
        }
    }

    private fun removeFood() {
        food.forEach {
            if (it.body.position.x < -10 || it.body.position.y > 10 ||
                    it.body.position.y < -10 || it.body.position.y > 10) {
                foodToRemove.add(it)
            }
        }

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

    private fun removeBoosters() {
        if (boostersToRemove.isNotEmpty()) {
            boostersToRemove.forEach {
                world.destroyBody(it.body)
                boosters.remove(it)
            }
            boostersToRemove.clear()
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
        boosters.clear()
        boostersToRemove.clear()
        whatToEat.clear()

        playersToAdd = 0

        timeSinceSpawn = 100f
        timeSincePlayerSpawn = 0f
        timeSinceShoeSpawn = 0f
        timeSinceBoostSpawn = 0f
    }
}

fun random(from: Float, to: Float) = from + ThreadLocalRandom.current().nextFloat()  * (to- from)
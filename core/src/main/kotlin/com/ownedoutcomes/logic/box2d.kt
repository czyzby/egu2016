package com.ownedoutcomes.logic

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.ownedoutcomes.Runner
import com.ownedoutcomes.logic.entity.*
import com.ownedoutcomes.music.Theme
import com.ownedoutcomes.view.GameOver
import com.ownedoutcomes.view.NextLevel
import ktx.assets.asset
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
var currentGamePoints = 0
var currentGameLevel = 1

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
    val attackingFoods = gdxSetOf<Food>()

    val playersToRemove = gdxSetOf<Player>()
    val foodToRemove = gdxSetOf<Food>()
    val shoesToRemove = gdxSetOf<Shoe>()

    val boostersToRemove = gdxSetOf<FoodBooster>()

    private var timeSinceSpawn = 100f
    private var timeSincePlayerSpawn = 0f
    private var timeSinceShoeSpawn = 0f
    private var timeSinceBoostSpawn = 0f

    var cameraX = .0f;
    var cameraY = .0f;

    fun reload() {
        world = World(vec2(0f, 0f), true)
        world.setContactListener(ContactController(this))
        players.add(Player(world, inputController, Vector2(0f, 0f)).initiate())

        for (i in 1..40) {
            food.add(Food(world, Vector2(gameViewport.camera.position.x,
                    gameViewport.camera.position.y)).initiate())
        }
    }

    fun resize(width: Int, height: Int) {
        gameViewport.update(width, height)
    }

    fun update(delta: Float) {
        if (!players.isEmpty()) {
            updateCamera(delta)
        }

        inputController.update()

        world.step(delta, 8, 3)

        runEeaters()
        runFoodReduction()

        spawnFood(delta, Vector2(gameViewport.camera.position.x, gameViewport.camera.position.y))
        spawnShoe(delta, Vector2(gameViewport.camera.position.x, gameViewport.camera.position.y))

        spawnNewPlayers(delta, Vector2(gameViewport.camera.position.x, gameViewport.camera.position.y))
        spawnBoosters(delta, Vector2(gameViewport.camera.position.x, gameViewport.camera.position.y))

        players.forEach { it.update(delta) }
        food.forEach { it.update(delta) }
        shoes.forEach { it.update(delta) }
        boosters.forEach { it.update(delta) }

        removeFood()
        removePlayers()
        removeShoes()
        removeBoosters()

        if (checkIfNextLevelIsAvailable()) {
            moveToNextLevel()
        }
        // TODO add tutorial
        // TODO make fish-booster smaller
        // TODO add sounds
        // TODO add music
        renderer.render(world, gameViewport.camera.combined)
    }

    private fun updateCamera(delta: Float) {
        cameraX += (players.last().body.position.x - cameraX) * .8f * delta
        cameraY += (players.last().body.position.y - cameraY) * .8f * delta

        gameViewport.camera.position.set(
                cameraX,
                cameraY,
                0f)
        gameViewport.camera.update()
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

    private fun spawnFood(delta: Float, center: Vector2) {
        timeSinceSpawn += delta
        if (timeSinceSpawn > random(1f, 2f)) {
            food.add(Food(world, center).initiate())
            timeSinceSpawn = 0f
        }
    }

    private fun spawnShoe(delta: Float, center: Vector2) {
        timeSinceShoeSpawn += delta
        if (timeSinceShoeSpawn > random(10f, 20f)) {
            shoes.add(Shoe(world, center).initiate())
            timeSinceShoeSpawn = 0f
        }
    }

    private fun spawnBoosters(delta: Float, center: Vector2) {
        timeSinceBoostSpawn += delta
        if (timeSinceBoostSpawn > random(1f, 2f)) {
            boosters.add(FoodBooster(world, center).initiate())
            timeSinceBoostSpawn = 0f
        }
    }

    private fun spawnNewPlayers(delta: Float, center: Vector2) {
        if (playersToAdd > 0) {
            while (playersToAdd-- > 0) {
                players.add(Player(world, inputController, center).initiate())
            }
            asset<Sound>("newPlayer.wav").play()
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
            if (it.body.position.x < gameViewport.camera.position.x - 3 * gameWorldWidth ||
                    it.body.position.x > gameViewport.camera.position.x + 3 * gameWorldWidth ||
                    it.body.position.y < gameViewport.camera.position.y - 3 * gameWorldWidth ||
                    it.body.position.y > gameViewport.camera.position.y + 3 * gameWorldWidth) {
                foodToRemove.add(it)
            }
        }

        if (foodToRemove.isNotEmpty()) {
            foodToRemove.forEach {
                world.destroyBody(it.body)
                food.remove(it)
                asset<Sound>("burp.wav").play()
            }
            foodToRemove.clear()
        }
    }

    private fun removeShoes() {
        shoes.forEach {
            if (it.body.position.x < gameViewport.camera.position.x - 3 * gameWorldWidth ||
                    it.body.position.x > gameViewport.camera.position.x + 3 * gameWorldWidth ||
                    it.body.position.y < gameViewport.camera.position.y - 3 * gameWorldWidth ||
                    it.body.position.y > gameViewport.camera.position.y + 3 * gameWorldWidth) {
                shoesToRemove.add(it)
            }
        }

        if (shoesToRemove.isNotEmpty()) {
            shoesToRemove.forEach {
                world.destroyBody(it.body)
                shoes.remove(it)
            }
            shoesToRemove.clear()
        }
    }

    private fun removeBoosters() {
        boosters.forEach {
            if (it.body.position.x < gameViewport.camera.position.x - 3 * gameWorldWidth ||
                    it.body.position.x > gameViewport.camera.position.x + 3 * gameWorldWidth ||
                    it.body.position.y < gameViewport.camera.position.y - 3 * gameWorldWidth ||
                    it.body.position.y > gameViewport.camera.position.y + 3 * gameWorldWidth) {
                boostersToRemove.add(it)
            }
        }

        if (boostersToRemove.isNotEmpty()) {
            boostersToRemove.forEach {
                world.destroyBody(it.body)
                boosters.remove(it)
            }
            boostersToRemove.clear()
        }
    }

    private fun checkIfNextLevelIsAvailable() : Boolean {
        return currentGamePoints >= currentGameLevel * 100
    }

    private fun moveToNextLevel() {
        currentGameLevel++
        inject<Runner>().setCurrentView(inject<NextLevel>())
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

        attackingPlayers.clear()
        foodToReduce.clear()
        attackingFoods.clear()

        playersToAdd = 0

        timeSinceSpawn = 100f
        timeSincePlayerSpawn = 0f
        timeSinceShoeSpawn = 0f
        timeSinceBoostSpawn = 0f
    }
}

fun random(from: Float, to: Float) = from + ThreadLocalRandom.current().nextFloat() * (to - from)
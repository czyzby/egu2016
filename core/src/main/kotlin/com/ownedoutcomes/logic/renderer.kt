package com.ownedoutcomes.logic

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ktx.collections.gdxArrayOf


class GameRenderer(val gameController: GameController, val batch: Batch, skin: Skin) {
    private val playerAttackSprite = skin.atlas.createSprite("player0")
    private val playerSprite = skin.atlas.createSprite("player")
    private val enemySprite = skin.atlas.createSprite("enemy0")
    private val shoeSprite = skin.atlas.createSprite("but")
    private val herringSprite = skin.atlas.createSprite("herring")

    private val animations = gdxArrayOf<Animation>()

    private var stateTime = 0f

    init {
        playerSprite.setOriginCenter()
        playerAttackSprite.setOriginCenter()
        enemySprite.setOriginCenter()
        shoeSprite.setOriginCenter()
        herringSprite.setOriginCenter()
    }

    fun render(delta: Float) {
        println("renderuję skurczysynów")

        batch.projectionMatrix = gameController.gameViewport.camera.combined
        batch.begin()
        gameController.players.forEach {
            val playerSprite = Sprite(playerSprite)
            playerSprite.flip(true, false)
            val spriteSize = it.size * 2
            playerSprite.x = it.body.position.x - it.size
            playerSprite.y = it.body.position.y - it.size
            playerSprite.setSize(spriteSize, spriteSize)
            playerSprite.setOriginCenter()
            playerSprite.rotation = MathUtils.radiansToDegrees * it.angle
            playerSprite.draw(batch)
        }

        gameController.food.forEach {
            val enemySprite = Sprite(enemySprite)
            val spriteSize = it.size * 2
            enemySprite.x = it.body.position.x - it.size
            enemySprite.y = it.body.position.y - it.size
            enemySprite.setOrigin(enemySprite.x, enemySprite.y)
            enemySprite.setSize(spriteSize, spriteSize)
            enemySprite.flip(it.spawnedLeft, false)
            enemySprite.draw(batch)
        }

        gameController.shoes.forEach {
            val shoeSprite = Sprite(shoeSprite)
            val spriteSize = it.size * 2
            shoeSprite.x = it.body.position.x - it.size
            shoeSprite.y = it.body.position.y - it.size
            shoeSprite.setOrigin(shoeSprite.x, shoeSprite.y)
            shoeSprite.setSize(spriteSize, spriteSize)
            shoeSprite.draw(batch)
        }

        gameController.boosters.forEach {
            val herringSprite = Sprite(herringSprite)
            val spriteSize = it.size * 2
            herringSprite.x = it.body.position.x - it.size
            herringSprite.y = it.body.position.y - it.size
            herringSprite.setOrigin(herringSprite.x, herringSprite.y)
            herringSprite.setSize(spriteSize, spriteSize)
            herringSprite.flip(it.spawnedLeft, false)
            herringSprite.draw(batch)
        }

        gameController.attackingPlayers.forEach {
            println("SKURCZYSYNOW MOCNYCH Animacja atakujących - tworzenie")

            val playerSprite = Sprite(playerAttackSprite)
            playerSprite.flip(true, false)
            val spriteSize = it.size * 2
            playerSprite.x = it.body.position.x - it.size
            playerSprite.y = it.body.position.y - it.size
            playerSprite.setSize(spriteSize, spriteSize)
            playerSprite.setOriginCenter()
            playerSprite.rotation = MathUtils.radiansToDegrees * it.angle
            val textureRegion = TextureRegion(playerSprite)
            val animation = Animation(0.25f, textureRegion, textureRegion, textureRegion, textureRegion)
            animations.add(animation)
        }

        stateTime += delta

        animations.forEach {
            if(!it.isAnimationFinished(stateTime))
                println("SKURCZYSYNOW MOCNYCH Animacja atakujących - uruchomienie")
                batch.draw(it.getKeyFrame(stateTime), 0f, 0f)
        }

        gameController.attackingPlayers.clear()
        batch.end()
    }
}
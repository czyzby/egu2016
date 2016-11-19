package com.ownedoutcomes.logic

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Skin


class GameRenderer(val gameController: GameController, val batch: Batch, skin: Skin) {
    private val playerSprite = skin.atlas.createSprite("player0")
    private val enemySprite = skin.atlas.createSprite("enemy0")

    init {
        playerSprite.setOriginCenter()
        playerSprite.flip(true, false)
        enemySprite.setOriginCenter()
    }

    fun render(delta: Float) {
        batch.projectionMatrix = gameController.gameViewport.camera.combined
        batch.begin()
        gameController.players.forEach {
            val playerSprite = Sprite(playerSprite)
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
        batch.end()
    }
}
package com.ownedoutcomes.logic

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Skin

class GameRenderer(val gameController: GameController, val batch: Batch, skin: Skin) {
    private val playerSprite = skin.atlas.createSprite("player0")

    init {
        playerSprite.setOriginCenter()
    }

    fun render(delta: Float) {
        batch.projectionMatrix = gameController.gameViewport.camera.combined
        batch.begin()
        gameController.players.forEach {
            val spriteSize = it.size * 2
            playerSprite.x = it.body.position.x - it.size
            playerSprite.y = it.body.position.y - it.size
            playerSprite.setSize(spriteSize, spriteSize)
            playerSprite.draw(batch)
        }
        batch.end()
    }
}
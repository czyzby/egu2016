package com.ownedoutcomes.logic

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.ownedoutcomes.logic.entity.Player
import ktx.collections.gdxMapOf
import ktx.collections.isNotEmpty


class GameRenderer(val gameController: GameController, val batch: Batch, skin: Skin) {
    private val playerAttackSprite1 = skin.atlas.createSprite("fish2-1")
    private val playerAttackSprite2 = skin.atlas.createSprite("fish2-2")
    private val playerAttackSprite3 = skin.atlas.createSprite("fish2-3")
    private val playerAttackSprite4 = skin.atlas.createSprite("fish2-4")

    private val playerSprite = skin.atlas.createSprite("fish2-0")
    private val enemySprite = skin.atlas.createSprite("enemy0")
    private val shoeSprite = skin.atlas.createSprite("but")
    private val herringSprite = skin.atlas.createSprite("herring")

    private var stateTime = 0f

    val textureAttackRegion1 = TextureRegion(playerAttackSprite1)
    val textureAttackRegion2 = TextureRegion(playerAttackSprite2)
    val textureAttackRegion3 = TextureRegion(playerAttackSprite3)
    val textureAttackRegion4 = TextureRegion(playerAttackSprite4)


    val eatingPlayers = gdxMapOf<Player, Animation>()

    init {
        playerSprite.setOriginCenter()

        playerSprite.flip(true, false)
        playerAttackSprite1.flip(true, false)
        playerAttackSprite2.flip(true, false)
        playerAttackSprite3.flip(true, false)
        playerAttackSprite4.flip(true, false)

        playerAttackSprite1.setOriginCenter()
        playerAttackSprite2.setOriginCenter()
        playerAttackSprite3.setOriginCenter()
        playerAttackSprite4.setOriginCenter()
        enemySprite.setOriginCenter()
        shoeSprite.setOriginCenter()
        herringSprite.setOriginCenter()
    }

    fun render(delta: Float) {
        batch.projectionMatrix = gameController.gameViewport.camera.combined
        batch.begin()
        stateTime += delta

        gameController.players.forEach {
            if (!(gameController.attackingPlayers.contains(it) || it in eatingPlayers.keys())) {
                val playerSprite = Sprite(playerSprite)
                val spriteSize = it.size * 2
                playerSprite.x = it.body.position.x - it.size
                playerSprite.y = it.body.position.y - it.size
                playerSprite.setSize(spriteSize, spriteSize)
                playerSprite.setOriginCenter()
                playerSprite.rotation = MathUtils.radiansToDegrees * it.angle

                var rotation = playerSprite.rotation
                if(rotation > 180)
                    rotation -= 360

                if(playerSprite.rotation in 90..180 || playerSprite.rotation in -180..-90) {
                    playerSprite.flip(false, true)
                }
                playerSprite.draw(batch)
            }
        }

        if (gameController.attackingPlayers.isNotEmpty()) {
            stateTime = 0f
        }
        gameController.attackingPlayers.forEach {
            val eatingAnimation = Animation(1f / 16f, textureAttackRegion1, textureAttackRegion2, textureAttackRegion3, textureAttackRegion4)
            eatingAnimation.playMode = Animation.PlayMode.LOOP_PINGPONG
            eatingPlayers.put(it, eatingAnimation)
        }

        eatingPlayers.removeAll { !gameController.players.contains(it.key) }

        eatingPlayers.forEach {
            val player = it.key
            val animationSprite = Sprite(playerAttackSprite1)
            val spriteSize = player.size * 2

            animationSprite.x = player.body.position.x - player.size
            animationSprite.y = player.body.position.y - player.size
            animationSprite.setSize(spriteSize, spriteSize)
            animationSprite.setOriginCenter()
            animationSprite.rotation = MathUtils.radiansToDegrees * player.angle
            animationSprite.setRegion(it.value.getKeyFrame(stateTime))
            animationSprite.flip(true, false)
            var rotation = animationSprite.rotation
            if(rotation > 180)
                rotation -= 360

            if(rotation in 90..180 || rotation in -180..-90) {
                animationSprite.flip(false, true)
            }

            animationSprite.draw(batch)
        }

        eatingPlayers.removeAll { it.value.isAnimationFinished(stateTime) }

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

//        gameController.attackingPlayers.forEach {
//            println("SKURCZYSYNOW MOCNYCH Animacja atakujących - tworzenie")
//            val playerSprite = Sprite(playerAttackSprite)
//            playerSprite.flip(true, false)
//            val spriteSize = it.size * 2
//            playerSprite.x = it.body.position.x - it.size
//            playerSprite.y = it.body.position.y - it.size
//            playerSprite.setSize(spriteSize, spriteSize)
//            playerSprite.setOriginCenter()
//            playerSprite.rotation = MathUtils.radiansToDegrees * it.angle
//            val textureRegion = TextureRegion(playerSprite)
//            val animation = Animation(0.25f, textureRegion, textureRegion, textureRegion, textureRegion)
//            animations.add(animation)
//        }

        stateTime += delta




        gameController.attackingPlayers.clear()
        batch.end()
    }
}
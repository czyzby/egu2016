package com.ownedoutcomes.logic.entity

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.*
import com.ownedoutcomes.logic.halfGameWorldHeight
import com.ownedoutcomes.logic.halfGameWorldWidth

class Food(world: World) : AbstractEntity(world) {
    var size = getRandomSize()
    var spawnedLeft = false

    override fun createBody(world: World): Body {
        val circle = CircleShape()
        size = getRandomSize()
        spawnedLeft = MathUtils.randomBoolean()
        circle.radius = size
        val body = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            fixedRotation = true
            linearDamping = 1f
            position.x = if (spawnedLeft) -halfGameWorldWidth - 1 else halfGameWorldWidth + 1
            position.y = MathUtils.random(-halfGameWorldHeight, halfGameWorldHeight)
        }
        val fixture = FixtureDef().apply {
            shape = circle
            density = 20f
            friction = 0.3f
            restitution = 0.1f
        }
        val result = world.createBody(body)
        result.createFixture(fixture)
        return result
    }

    private fun getRandomSize(): Float {
        val random = MathUtils.random(-0.2f, 1f)
        return if (random < 0.1f) 0.1f else random // TODO uzaleznic od wielkosci gracza? eee makarena
    }

    override fun update(delta: Float) {
        if (spawnedLeft) {
            body.applyForceToCenter(1f * (size * 100f) * delta, 0f, true);
        } else {
            body.applyForceToCenter(-1f * (size * 100f) * delta, 0f, true);
        }
    }
}
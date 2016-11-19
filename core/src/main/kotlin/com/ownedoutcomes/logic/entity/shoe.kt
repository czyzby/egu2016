package com.ownedoutcomes.logic.entity

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.*
import com.ownedoutcomes.logic.halfGameWorldHeight
import com.ownedoutcomes.logic.halfGameWorldWidth

class Shoe(world: World) : AbstractEntity(world) {
    var size = getRandomSize()
    var speedBonus = MathUtils.random(0.8f, 1.5f)

    override fun createBody(world: World): Body {
        val circle = CircleShape()
        size = getRandomSize()
        circle.radius = size
        val body = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            fixedRotation = true
            linearDamping = 1f
            position.x = MathUtils.random(-halfGameWorldWidth, halfGameWorldWidth)
            position.y = halfGameWorldHeight + 2f
        }
        val fixture = FixtureDef().apply {
            shape = circle
            density = 20f
            friction = 0.3f
            restitution = 0.1f
            filter.categoryBits = shoeGroup
            filter.maskBits = shoeCollisionGroup
        }
        val result = world.createBody(body)
        result.createFixture(fixture).userData = this
        return result
    }

    override fun initiate(): Shoe = super.initiate() as Shoe

    private fun getRandomSize(): Float {
        val random = Math.abs(MathUtils.random(-0.4f, 1f))
        return if (random < 0.1f) 0.1f else random // TODO uzaleznic od wielkosci gracza? eee makarena
    }

    override fun update(delta: Float) {
        val currentDensity = size * size * MathUtils.PI * playerDensity * speedBonus * MathUtils.random(0.9f, 1.1f)
        body.applyForceToCenter(
                body.linearVelocity.x * currentDensity / 4f,
                body.linearVelocity.y * currentDensity / 4f,
                true)
        body.applyForceToCenter(0f, -currentDensity, true)
    }
}
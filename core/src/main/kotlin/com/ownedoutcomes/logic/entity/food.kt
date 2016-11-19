package com.ownedoutcomes.logic.entity

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.*
import com.ownedoutcomes.logic.halfGameWorldHeight
import com.ownedoutcomes.logic.halfGameWorldWidth

class Food(world: World) : AbstractEntity(world) {
    var size = getRandomSize()
    var spawnedLeft = false
    var speedBonus = MathUtils.random(0.8f, 1.5f)

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
            filter.categoryBits = foodGroup
            filter.maskBits = foodCollisionGroup
        }
        val result = world.createBody(body)
        result.createFixture(fixture).userData = this
        return result
    }

    override fun initiate(): Food = super.initiate() as Food

    private fun getRandomSize(): Float {
        val random = Math.abs(MathUtils.random(-0.4f, 1f))
        return if (random < 0.1f) 0.1f else random // TODO uzaleznic od wielkosci gracza? eee makarena
    }

    override fun update(delta: Float) {
        val currentDensity = size * size * MathUtils.PI * playerDensity * speedBonus * MathUtils.random(0.9f, 1.1f)
        body.applyForceToCenter(
                -body.linearVelocity.x * currentDensity / 4f,
                -body.linearVelocity.y * currentDensity / 4f,
                true)
        if (spawnedLeft) {
            body.applyForceToCenter(currentDensity, 0f, true);
        } else {
            body.applyForceToCenter(-currentDensity, 0f, true);
        }

    }

    fun eat(food: FoodBooster) {
        size += food.size / 10f
        body.fixtureList.first().shape.radius = size
    }

    fun eat(food: Player) {
        size += food.size / 10f
        body.fixtureList.first().shape.radius = size
    }
}
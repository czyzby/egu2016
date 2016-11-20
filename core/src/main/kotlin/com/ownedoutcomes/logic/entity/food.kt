package com.ownedoutcomes.logic.entity

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.ownedoutcomes.logic.gameWorldHeight
import com.ownedoutcomes.logic.gameWorldWidth
import com.ownedoutcomes.logic.halfGameWorldWidth
import com.ownedoutcomes.logic.random

class Food(world: World, center: Vector2) : AbstractEntity(world) {

    var size = getRandomSize()
    var spawnedLeft = false
    var speedBonus = random(0.8f, 1.5f)
    var spawnCenter: Vector2 = center

    override fun createBody(world: World): Body {
        val circle = CircleShape()
        size = getRandomSize()
        spawnedLeft = MathUtils.randomBoolean()
        circle.radius = size
        val body = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            fixedRotation = true
            linearDamping = 1f
            position.x = if (spawnedLeft)
                random(spawnCenter.x - 2 * gameWorldWidth, -halfGameWorldWidth)
            else
                random(halfGameWorldWidth, spawnCenter.x + 2 * gameWorldHeight)
            position.y = random(spawnCenter.y - 2 * gameWorldHeight, spawnCenter.y + 2 * gameWorldHeight)
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
        circle.dispose()
        return result
    }

    override fun initiate(): Food = super.initiate() as Food

    private fun getRandomSize(): Float {
        val random = Math.abs(random(-0.4f, 1f))
        return if (random < 0.1f) 0.1f else random // TODO uzaleznic od wielkosci gracza? eee makarena
    }

    override fun update(delta: Float) {
        val currentDensity = size * size * MathUtils.PI * playerDensity * speedBonus * random(0.9f, 1.1f)
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

    override fun eat(entity: AbstractEntity) {
        if (entity is Player) {
            eat(entity)
        } else if (entity is FoodBooster) {
            eat(entity)
        }
    }

    fun reduce(factor: Float) {
        size -= this.size * factor
        body.fixtureList.first().shape.radius = size
    }
}
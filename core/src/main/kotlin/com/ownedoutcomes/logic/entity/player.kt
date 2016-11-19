package com.ownedoutcomes.logic.entity

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.*
import com.ownedoutcomes.logic.InputController

val playerDensity = 20f

class Player(world: World, val inputController: InputController) : AbstractEntity(world) {
    var size: Float = 0.2f
    var angle: Float = 0.0f;

    override fun createBody(world: World): Body {
        val circle = CircleShape()
        circle.radius = 0.2f
        val body = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            fixedRotation = true
            linearDamping = 1f
        }
        val fixture = FixtureDef().apply {
            shape = circle
            density = playerDensity
            friction = 0.3f
            restitution = 0.1f
            filter.categoryBits = playerGroup
            filter.maskBits = playerCollisionGroup
        }
        val result = world.createBody(body)
        result.createFixture(fixture).userData = this
        return result
    }

    override fun initiate(): Player = super.initiate() as Player

    override fun update(delta: Float) {
        val currentDensity = 15f + size * size * MathUtils.PI * playerDensity * 1.05f
        body.applyForceToCenter(
                -body.linearVelocity.x * currentDensity / 4f,
                -body.linearVelocity.y * currentDensity / 4f,
                true)
        if (body.fixtureList.first().testPoint(inputController.x, inputController.y)) {
            return
        }
        angle = MathUtils.atan2(inputController.y - body.position.y, inputController.x - body.position.x)
        val xForce = MathUtils.cos(angle);
        val yForce = MathUtils.sin(angle);
        body.applyForceToCenter(
                xForce * currentDensity,
                yForce * currentDensity,
                true)
    }

    fun enlarge() {
        size += 0.2f
        body.fixtureList.first().shape.radius = size
    }

    fun smaller() {
        size -= 0.2f
        body.fixtureList.first().shape.radius = size
    }

    fun eat(food: Food) {
        size += food.size / 10f
        body.fixtureList.first().shape.radius = size
    }
}
package com.ownedoutcomes.logic.entity

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.*
import com.ownedoutcomes.logic.InputController

class Player(world: World, val inputController: InputController) : AbstractEntity(world) {
    override fun createBody(world: World): Body {
        val circle = CircleShape()
        circle.radius = 0.5f
        val body = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            fixedRotation = true
            linearDamping = 1f
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

    override fun update(delta: Float) {
        val angle = MathUtils.atan2(inputController.y - body.position.y, inputController.x - body.position.x)
        val xForce = MathUtils.cos(angle);
        val yForce = MathUtils.sin(angle);
        val bodyFixture = body.fixtureList[0];

        val shapeRadius = bodyFixture.shape.radius - bodyFixture.shape.radius * 0.001f
        bodyFixture.shape.radius = shapeRadius
//        println("shapeRadius = $shapeRadius - radius = $bodyFixture.shape.radius")
        val negativeXForce = -(xForce * (1f - shapeRadius) * 10)
        val negativeYForce = -(yForce * (1f - shapeRadius) * 10)
        body.applyForceToCenter(negativeXForce, negativeYForce, true)
        val positiveXForce = xForce * (1f - shapeRadius) * 100
        val positiveYForce = yForce * (1f - shapeRadius) * 100
        body.applyForceToCenter(positiveXForce, positiveYForce, true)
//        println("negative: X = $negativeXForce, Y = $negativeYForce")
//        println("positive: X = $positiveXForce, Y = $positiveYForce")
    }
}
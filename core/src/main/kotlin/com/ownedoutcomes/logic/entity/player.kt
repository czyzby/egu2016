package com.ownedoutcomes.logic.entity

import com.badlogic.gdx.physics.box2d.*

class Player(world: World) : AbstractEntity(world) {
    override fun createBody(world: World): Body {
        val circle = CircleShape()
        circle.radius = 0.5f
        val body = BodyDef().apply {
            type = BodyDef.BodyType.DynamicBody
            fixedRotation = true
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
}
package com.ownedoutcomes.logic.entity

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World

interface Entity {
    fun update(delta: Float)
    fun initiate(): Entity
}

abstract class AbstractEntity(val world: World) : Entity {
    lateinit var body: Body

    override fun initiate(): AbstractEntity {
        body = createBody(world)
        return this;
    }

    abstract fun createBody(world: World): Body

    override fun update(delta: Float) {
    }
}

// Collision groups:
val foodGroup: Short = 0b00000001
val shoeGroup: Short = 0b00000001

val playerGroup: Short = 0b00000010

val playerCollisionGroup: Short = 0b00000011
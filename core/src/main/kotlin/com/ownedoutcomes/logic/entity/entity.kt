package com.ownedoutcomes.logic.entity

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World

interface Entity {
    fun update(delta: Float)
}

abstract class AbstractEntity(val world: World) : Entity {
    val body: Body = createBody(world)

    abstract fun createBody(world: World): Body

    override fun update(delta: Float) {
    }
}
package com.ownedoutcomes.logic.entity

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*

class Bound(world: World) : AbstractEntity(world) {
    override fun createBody(world: World): Body {
        val polygon = ChainShape()

//        polygon.createChain(arrayOf(
//                Vector2(-halfGameWorldWidth, -halfGameWorldHeight),
//                Vector2(halfGameWorldWidth, -halfGameWorldHeight),
//                Vector2(halfGameWorldWidth, halfGameWorldHeight),
//                Vector2(-halfGameWorldWidth, halfGameWorldHeight)))

        polygon.createChain(arrayOf(
                Vector2(-1f, -1f),
                Vector2(1f, -1f),
                Vector2(1f, 1f),
                Vector2(-1f, 1f)))

        val body = BodyDef().apply {
            type = BodyDef.BodyType.StaticBody
        }

        val fixture = FixtureDef().apply {
            shape = polygon
        }

        val result = world.createBody(body)
        result.createFixture(fixture).userData = this
        return result
    }

    override fun initiate(): Bound = super.initiate() as Bound

}
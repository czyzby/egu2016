package com.ownedoutcomes.logic

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.ownedoutcomes.logic.entity.Food
import com.ownedoutcomes.logic.entity.FoodBooster
import com.ownedoutcomes.logic.entity.Player

class ContactController(val gameController: GameController) : ContactListener {
    override fun endContact(contact: Contact) {
    }

    override fun beginContact(contact: Contact) {
        checkContact(contact.fixtureA.userData, contact.fixtureB.userData)
        checkContact(contact.fixtureB.userData, contact.fixtureA.userData)
    }

    private fun checkContact(firstEntity: Any, secondEntity: Any) {
        if (firstEntity is Food && secondEntity is Player) {
            if (firstEntity.size > secondEntity.size * 1.05) {
                gameController.playersToRemove.add(secondEntity)
                val factor = secondEntity.size / firstEntity.size
                gameController.foodToReduce.add(Pair(firstEntity, factor))
                gameController.whatToEat.add(Pair(firstEntity, secondEntity))
            } else {
                gameController.foodToRemove.add(firstEntity)
                gameController.whatToEat.add(Pair(secondEntity, firstEntity))
                gameController.attackingPlayers.add(secondEntity)

                gameController.points += 10
            }
        }

        if (firstEntity is FoodBooster && secondEntity is Player) {
            gameController.playersToAdd++
            gameController.boostersToRemove.add(firstEntity)

            gameController.points += 5
        }

        if (firstEntity is FoodBooster && secondEntity is Food) {
            if (firstEntity.size < secondEntity.size * 1.05) {
                gameController.boostersToRemove.add(firstEntity)
                gameController.whatToEat.add(Pair(secondEntity, firstEntity))
            }
        }

        if (firstEntity is Player && secondEntity is Player) {
            val secondEntityPosition = secondEntity.body.position
            val firstEntityPosition = firstEntity.body.position
            val angle = MathUtils.atan2(secondEntityPosition.y - firstEntityPosition.y, secondEntityPosition.x - firstEntityPosition.x)
            val xForce = MathUtils.cos(angle);
            val yForce = MathUtils.sin(angle);
            firstEntity.body.applyForceToCenter(
                    xForce * 100f,
                    yForce * 100f,
                    true)
        }
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
    }
}
package com.ownedoutcomes.logic

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.ownedoutcomes.logic.entity.Bound
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
            } else {
                gameController.foodToRemove.add(firstEntity)
                secondEntity.eat(firstEntity)
            }
        }

        if (firstEntity is FoodBooster && secondEntity is Player) {
            val newPlayer = Player(secondEntity.world, secondEntity.inputController)
            gameController.playersToAdd.add(newPlayer)
            gameController.boostersToRemove.add(firstEntity)
        }

        if (firstEntity is FoodBooster && secondEntity is Food) {
            if (firstEntity.size < secondEntity.size * 1.05) {
                gameController.boostersToRemove.add(firstEntity)
            }
        }

        if (firstEntity is Food && secondEntity is Bound) {
            gameController.foodToRemove.add(secondEntity as Food)
        }
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
    }
}
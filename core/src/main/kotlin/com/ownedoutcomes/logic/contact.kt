package com.ownedoutcomes.logic

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
        println("sprawdzam kolizje pomiędzy $firstEntity a $secondEntity")
        if (firstEntity is Food && secondEntity is Player) {
            if (firstEntity.size > secondEntity.size * 1.05) {
//                val sizeFactor = (secondEntity.size / firstEntity.size)
//                firstEntity.body.fixtureList[0].shape.radius -=  sizeFactor * firstEntity.body.fixtureList[0].shape.radius
//                firstEntity.size -= sizeFactor  * firstEntity.size
                gameController.playersToRemove.add(secondEntity)
                secondEntity.eat(firstEntity)
            } else {
                gameController.foodToRemove.add(firstEntity)
                secondEntity.eat(firstEntity)
            }
        }

        if (firstEntity is FoodBooster && secondEntity is Player) {
            gameController.playersToAdd++
            gameController.boostersToRemove.add(firstEntity)
        }

        if (firstEntity is FoodBooster && secondEntity is Food) {
            if (firstEntity.size < secondEntity.size * 1.05) {
                gameController.boostersToRemove.add(firstEntity)
                secondEntity.eat(firstEntity)
            }
        }
        println("skończyłem sprawdzać kolizje pomiędzy $firstEntity a $secondEntity")
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
    }
}
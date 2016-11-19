package com.ownedoutcomes.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ktx.assets.load
import ktx.assets.loadOnDemand
import ktx.style.button
import ktx.style.skin

fun loadSkin() = skin(atlas = loadOnDemand<TextureAtlas>(path = "skin.atlas").asset) {
    button("play") {
        up = getDrawable("play-button")
        over = getDrawable("play-button-over")
        down = getDrawable("play-button-down")
    }
}

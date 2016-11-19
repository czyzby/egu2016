package com.ownedoutcomes.asset

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ktx.assets.loadOnDemand
import ktx.style.skin
import ktx.style.textButton

fun loadSkin() = skin(atlas = loadOnDemand<TextureAtlas>(path = "skin.atlas").asset) {
    textButton("play-button") {
        font = BitmapFont()
        up = getDrawable("button")
        over = getDrawable("button-pressed")
        down = getDrawable("button-pressed")
    }
}

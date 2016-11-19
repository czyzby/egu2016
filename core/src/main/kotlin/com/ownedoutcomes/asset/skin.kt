package com.ownedoutcomes.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ktx.assets.loadOnDemand
import ktx.style.*

fun loadSkin() = skin(atlas = loadOnDemand<TextureAtlas>(path = "skin.atlas").asset) {
    textButton("play-button") {
        font = BitmapFont(Gdx.files.internal("font-export.fnt"), getRegion("font-export"), false)
        up = getDrawable("button")
        over = getDrawable("button-pressed")
        down = getDrawable("button-pressed")
    }

    window("game-over") {
        titleFont = BitmapFont(Gdx.files.internal("font-export.fnt"), getRegion("font-export"), false)
    }

    textButton("game-over-button") {
        font = BitmapFont(Gdx.files.internal("font-export.fnt"), getRegion("font-export"), false)
        up = getDrawable("button")
        over = getDrawable("button-pressed")
        down = getDrawable("button-pressed")
    }
}

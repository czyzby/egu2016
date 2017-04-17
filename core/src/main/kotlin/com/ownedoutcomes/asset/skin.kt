package com.ownedoutcomes.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ktx.assets.loadOnDemand
import ktx.style.*

fun loadSkin() = skin(atlas = loadOnDemand<TextureAtlas>(path = "skin.atlas").asset) {
    val bitmapFont = BitmapFont(Gdx.files.internal("font-export.fnt"), getRegion("font-export"), false)
    textButton("play-button") {
        font = bitmapFont
        up = getDrawable("button")
        over = getDrawable("button-pressed")
        down = getDrawable("button-pressed")
    }

    window("game-over") {
        titleFont = bitmapFont
    }

    textButton("game-over-button") {
        font = bitmapFont
        up = getDrawable("button")
        over = getDrawable("button-pressed")
        down = getDrawable("button-pressed")
    }

    label("game-over") {
        font = bitmapFont
    }

    label("points") {
        font = bitmapFont
    }

    button("start") {
        up = getDrawable("start-up")
        over = getDrawable("start-over")
        down = getDrawable("start-down")
    }

    button("to-menu") {
        up = getDrawable("to-menu")
    }

    button("play-again") {
        up = getDrawable("play-again")
    }
}

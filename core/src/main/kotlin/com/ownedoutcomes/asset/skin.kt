package com.ownedoutcomes.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ktx.assets.loadOnDemand
import ktx.style.skin
import ktx.style.textButton
import ktx.style.textField
import ktx.style.window

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

    textField("game-over") {
        font = bitmapFont
        messageFont= bitmapFont
        fontColor = Color.PINK
    }
}

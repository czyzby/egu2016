package com.ownedoutcomes.desktop

import com.badlogic.gdx.Files
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.ownedoutcomes.Runner

object DesktopLauncher {
    @JvmStatic fun main(args: Array<String>) {
        createApplication()
    }

    private fun createApplication() = LwjglApplication(Runner(), defaultConfiguration)

    private val defaultConfiguration: LwjglApplicationConfiguration
        get() {
            val configuration = LwjglApplicationConfiguration()
            configuration.title = "EGU2016"
            configuration.width = 800
            configuration.height = 600
            for (size in intArrayOf(128, 64, 32, 16)) {
                configuration.addIcon("libgdx$size.png", Files.FileType.Internal)
            }
            return configuration
        }
}
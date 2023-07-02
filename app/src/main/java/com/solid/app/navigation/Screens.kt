package com.solid.app.navigation

import com.solid.app.BuildConfig
import com.solid.navigation.destination.DestinationFactory


object Screens {
    private val factory by lazy { DestinationFactory(BuildConfig.DYNAMIC_LINK_DOMAIN) }

    val Home = factory.create("home")
    val Settings = factory.create("settings")
}
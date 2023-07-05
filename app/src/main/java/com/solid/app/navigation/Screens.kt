package com.solid.app.navigation

import com.solid.app.BuildConfig
import com.solid.navigation.destination.DestinationFactory


object Screens {
    private val factory by lazy { DestinationFactory(BuildConfig.DYNAMIC_LINK_DOMAIN) }

    val Today = factory.create("today")
    val Month = factory.create("month")
    val Year = factory.create("year")
    val Stats = factory.create("stats")
    val Settings = factory.create("settings")
}
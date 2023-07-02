package com.solid.navigation.destination

import android.content.Intent
import android.net.Uri
import androidx.navigation.navDeepLink
import com.solid.navigation.destination.Destination

class DestinationFactory(private val domain: String) {

    private fun buildRoute(path: String, queryParameters: Array<out String>): String {
        val uri = Uri.Builder()
            .scheme("https")
            .authority(domain)
            .appendPath(path)
            .apply {
                queryParameters.forEach {
                    appendQueryParameter(it, "{${it}}")
                }
            }
            .build()
        return Uri.decode(uri.toString())
    }

    fun create(
        path: String,
        vararg queryParameters: String
    ): Destination {
        val url = buildRoute(path, queryParameters)
        return Destination(
            route = url,
            arguments = queryParameters.toList(),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = url
                    action = Intent.ACTION_VIEW
                }
            )
        )
    }
}
package com.solid.remoteconfig

import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfig @Inject constructor(private val remoteConfig: FirebaseRemoteConfig) {

    fun fetch() {
        remoteConfig.fetch()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Timber.tag("nt.dung").d("Fetch successfully!")
                    remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
                        override fun onUpdate(configUpdate: ConfigUpdate) {
                            configUpdate.updatedKeys.forEach {
                                Timber.tag("nt.dung").d("Remote config updated key $it!")
                            }
                        }

                        override fun onError(error: FirebaseRemoteConfigException) {
                            Timber.tag("nt.dung").e(error)
                        }
                    })
                } else {
                    Timber.e(it.exception)
                }
            }
    }

    fun activate() {
        remoteConfig.activate()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result) {
                        Timber.tag("nt.dung").d("Activate successfully!")
                    } else {
                        Timber.tag("nt.dung").d("Remote config were activated in previous call!")
                    }
                } else {
                    Timber.e(it.exception)
                }
            }
    }

    fun getString(key: String) : String {
        return remoteConfig.getString(key)
    }
}
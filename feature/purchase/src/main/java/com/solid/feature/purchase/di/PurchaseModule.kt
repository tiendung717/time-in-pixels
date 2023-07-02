package com.solid.feature.purchase.di

import com.android.billingclient.api.BillingClient
import com.solid.feature.purchase.BuildConfig
import com.solid.feature.purchase.model.PurchaseItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PurchaseModule {

    @BillingLicenseKey
    @Provides
    @Singleton
    fun provideBillingLicenseKey(): String = BuildConfig.LicenseKey

    @PurchaseCoroutineScope
    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    @PurchaseItems
    @Provides
    @Singleton
    fun providePurchaseList(): List<PurchaseItem> {
        return listOf(
            BuildConfig.MonthlySubscription,
            BuildConfig.YearlySubscription
        ).map {
            PurchaseItem(
                id = it.trim(),
                type = BillingClient.ProductType.SUBS
            )
        }
    }
}
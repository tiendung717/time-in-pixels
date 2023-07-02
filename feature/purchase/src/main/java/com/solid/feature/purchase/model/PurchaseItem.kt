package com.solid.feature.purchase.model

import com.android.billingclient.api.BillingClient.ProductType

data class PurchaseItem(
    val id: String,
    @ProductType val type: String
)
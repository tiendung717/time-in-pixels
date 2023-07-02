package com.solid.amz.purchase

sealed class AmzSku(val sku: String) {
    object Subscription: AmzSku("com.alteox.com.duckTV-monthly")
}
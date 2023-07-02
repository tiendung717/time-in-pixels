package com.solid.amz.purchase.viewmodel

import androidx.lifecycle.ViewModel
import com.solid.amz.purchase.AmzBilling
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AmzPurchaseViewModel @Inject constructor(
    private val billing: AmzBilling
) : ViewModel() {

    fun buy(sku: String) {
        billing.buy(sku)
    }

    fun isPurchased(productId: String) = billing.isPurchased(productId)

    fun restorePurchase() {
        billing.restorePurchase()
    }
}
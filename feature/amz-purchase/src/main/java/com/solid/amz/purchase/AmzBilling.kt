package com.solid.amz.purchase

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.amazon.device.iap.PurchasingListener
import com.amazon.device.iap.PurchasingService
import com.amazon.device.iap.model.ProductDataResponse
import com.amazon.device.iap.model.PurchaseResponse
import com.amazon.device.iap.model.PurchaseUpdatesResponse
import com.amazon.device.iap.model.Receipt
import com.amazon.device.iap.model.UserDataResponse
import com.solid.amz.purchase.di.AmzPurchaseCoroutineScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AmzBilling @Inject constructor(
    @ApplicationContext private val context: Context,
    @AmzPurchaseCoroutineScope private val coroutineScope: CoroutineScope
) {
    private val Context.dataStore by preferencesDataStore(name = "amz_purchase.ds")

    private val purchaseListener = object : PurchasingListener {
        override fun onUserDataResponse(userDataResponse: UserDataResponse?) {
            userDataResponse?.let { response ->
                Timber.tag("nt.dung").d("User ID: ${response.userData.userId}")
            }
        }

        override fun onProductDataResponse(productDataResponse: ProductDataResponse?) {
            productDataResponse?.let { response ->
                response.productData.forEach {
                    Timber.tag("nt.dung").d("SKU: ${it.key}")
                }
            }
        }

        override fun onPurchaseResponse(purchaseResponse: PurchaseResponse?) {
           purchaseResponse?.let { response ->
               handlePurchase(response.receipt)
           }
        }

        override fun onPurchaseUpdatesResponse(purchaseUpdateResponse: PurchaseUpdatesResponse?) {
            purchaseUpdateResponse?.let { response ->
                val status = response.requestStatus
                when (status) {
                    PurchaseUpdatesResponse.RequestStatus.SUCCESSFUL -> {
                        Timber.tag("nt.dung").d("Receipts size: ${response.receipts.size}")
                        response.receipts.forEach {
                            handlePurchase(it)
                        }
                        if (response.hasMore()) {
                            PurchasingService.getPurchaseUpdates(false)
                        }
                    }
                    PurchaseUpdatesResponse.RequestStatus.FAILED -> {
                        Timber.tag("nt.dung").e("Purchase update failed!!!")
                    }

                    else -> {
                        Timber.tag("nt.dung").e("Purchase update status: $status")
                    }
                }
            }
        }
    }

    fun init() {
        PurchasingService.registerListener(context, purchaseListener)
        PurchasingService.enablePendingPurchases()
    }

    fun buy(sku: String) {
        PurchasingService.purchase(sku)
    }

    fun isPurchased(productId: String): Flow<Boolean> {
        val prefKey = booleanPreferencesKey(productId)
        return context.dataStore.data.map { it[prefKey] ?: false }
    }


    fun handlePurchase(receipt: Receipt) {
        if (receipt.isCanceled) {
            savePurchaseStatus(receipt.termSku, false)
        } else {
            savePurchaseStatus(receipt.termSku, true)
        }
    }

    fun restorePurchase() {
        PurchasingService.getUserData()
        PurchasingService.getProductData(setOf(AmzSku.Subscription.sku))
        PurchasingService.getPurchaseUpdates(false)
    }

    private fun savePurchaseStatus(productId: String, purchased: Boolean) {
        coroutineScope.launch {
            context.dataStore.edit { settings ->
                val prefKey = booleanPreferencesKey(productId)
                settings[prefKey] = purchased
            }
        }
    }
}
package com.solid.feature.purchase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import com.solid.feature.purchase.viewmodel.PurchaseViewModel

@Composable
fun subscribedState(): State<Boolean> {
    val purchaseViewModel = hiltViewModel<PurchaseViewModel>()
    return produceState(initialValue = false) {
        purchaseViewModel.hasSubscribed().collect { value = it }
    }
}
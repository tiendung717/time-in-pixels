package com.solid.common.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend CoroutineScope.() -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(call())
        } catch (throwable: Throwable) {
            ResultWrapper.error(throwable)
        }
    }
}

fun <T> flowApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    dataQuery: suspend () -> T
): Flow<ResultWrapper<T>> {
    return flow {
        emit(ResultWrapper.Loading)
        emit(ResultWrapper.Success(dataQuery()))
    }
        .catch { emit(ResultWrapper.Error(it)) }
        .flowOn(dispatcher)
}

@Composable
fun <T : R, R> Flow<T>.collectApiAsState(
    initial: R,
    key: Any?,
    context: CoroutineContext = EmptyCoroutineContext
): State<R> = produceState(initial, key, context) {
    if (context == EmptyCoroutineContext) {
        collect { value = it }
    } else withContext(context) {
        collect { value = it }
    }
}
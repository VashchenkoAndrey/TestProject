package com.foamline.osm

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchWhenCreated(
    scope: LifecycleCoroutineScope,
    body: suspend (value: T) -> Unit
): Job {
    return scope.launchWhenCreated { collect(body) }
}

fun <T> Flow<T>.launchWhenResumed(
    scope: LifecycleCoroutineScope,
    body: suspend (value: T) -> Unit
): Job {
    return scope.launchWhenResumed { collect(body) }
}

fun <T> Flow<T>.launchWhenStarted(
    scope: LifecycleCoroutineScope,
    body: suspend (value: T) -> Unit
): Job {
    return scope.launchWhenStarted { collect(body) }
}

fun <T> Flow<T>.launch(
    scope: CoroutineScope,
    body: suspend (value: T) -> Unit
): Job {
    return scope.launch { collect(body) }
}
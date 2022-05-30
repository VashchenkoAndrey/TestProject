package com.foamline.osm

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.coroutines.suspendCoroutine
import kotlin.math.roundToInt

fun Int.px(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return (toFloat() * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun Int.dp(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return (toFloat() * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

suspend fun View.post() = suspendCoroutine<Unit> {
    post { it.resumeWith(Result.success(Unit)) }
}

suspend fun View.safePost(owner: LifecycleOwner) = suspendCoroutine<Unit> {
    safePost(owner) { it.resumeWith(Result.success(Unit)) }
}

fun View.safePost(owner: LifecycleOwner, runnable: Runnable) {
    owner.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            removeCallbacks(runnable)
        }
    })
    post(runnable)
}
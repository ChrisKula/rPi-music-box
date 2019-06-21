package com.christiankula.rpimusicbox.commons.rxjava2

import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Simple [Disposable] that will execute its [onDispose] method when disposed
 */
abstract class SimpleDisposable : Disposable {

    private val unsubscribed = AtomicBoolean()

    override fun isDisposed(): Boolean {
        return unsubscribed.get()
    }

    override fun dispose() {
        if (unsubscribed.compareAndSet(false, true)) {
            onDispose()
        }
    }

    abstract fun onDispose()
}

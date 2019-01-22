package com.christiankula.rpimusicbox.remote.rxjava2

import io.reactivex.android.MainThreadDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Simple [Disposable] that will execute its [onDispose] method when disposed.
 *
 * Inspired from [MainThreadDisposable] but doesn't execute [onDispose] on a particular Thread
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

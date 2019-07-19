package rpimusicbox.libraries.commons.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Convenient extension function to enable chaining Rx calls
 */
fun Disposable.disposeBy(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

/**
 * Returns `true` if this nullable Disposable is either null or disposed.
 */
fun Disposable?.isNullOrDisposed(): Boolean {
    return this == null || this.isDisposed
}

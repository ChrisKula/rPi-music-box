package com.christiankula.rpimusicbox.remote.extensions

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

/**
 * Convenient method to replace the container's Fragment, referenced by the given containerViewId,
 * by the given Fragment
 *
 * @param containerViewId Identifier of the container whose fragment(s) are to be replaced.
 * @param fragment The new fragment to place in the container.
 * @param tag Optional tag name for the fragment
 * @param shouldAddToBackStack whether the Fragment should be added to the back stack, default to false
 * @param transition which transition to use among recommended to use by [FragmentTransaction.setTransition], default [FragmentTransaction.TRANSIT_FRAGMENT_FADE]
 *
 */
fun AppCompatActivity.replaceFragment(@IdRes containerViewId: Int, fragment: Fragment, tag: String,
                                      shouldAddToBackStack: Boolean = false,
                                      transition: Int = FragmentTransaction.TRANSIT_FRAGMENT_FADE) {
    supportFragmentManager.beginTransaction()
            .replace(containerViewId, fragment, tag)
            .setTransition(transition)
            .apply {
                if (shouldAddToBackStack) {
                    addToBackStack(tag)
                }
            }
            .commit()
}

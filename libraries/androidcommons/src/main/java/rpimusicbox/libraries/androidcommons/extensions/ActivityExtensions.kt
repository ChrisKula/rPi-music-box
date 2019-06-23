package rpimusicbox.libraries.androidcommons.extensions

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

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

/**
 * Open the App's settings screen in a new Activity
 */
fun AppCompatActivity.goToAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

    startActivity(intent)
}

/**
 * Convenient method that find the Fragment by the given tag and then safelly cast it to the given type
 *
 * Returns `null` if there's no Fragment with the given tag or if the found Fragment couldn't be casted
 */
inline fun <reified T : Fragment> AppCompatActivity.findFragmentByTag(tag: String?): T? {
    return supportFragmentManager.findFragmentByTag(tag) as? T
}

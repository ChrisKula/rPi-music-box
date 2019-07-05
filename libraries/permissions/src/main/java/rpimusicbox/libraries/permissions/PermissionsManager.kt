package rpimusicbox.libraries.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionsManager(private val context: Context) {

    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Return an [PermissionRequestResult] of the given (single) permission according to the given grant results
     *
     * Return [PermissionRequestResult.Error] if [grantResults]'s size is different than 1
     *
     * @param activity the Activity to check against whether a rationale should be shown
     * @param requestedPermission the requested Permission
     * @param grantResults the grant results given back by `onRequestPermissionsResult` callback
     */
    fun handlePermissionRequestResult(activity: Activity,
                                      requestedPermission: String,
                                      grantResults: IntArray): PermissionRequestResult {
        return when {
            grantResults.size != 1 -> PermissionRequestResult.Error
            grantResults[0] == PackageManager.PERMISSION_GRANTED -> PermissionRequestResult.PermissionGranted
            ActivityCompat.shouldShowRequestPermissionRationale(activity, requestedPermission) -> PermissionRequestResult.ShouldShowRationale
            else -> PermissionRequestResult.PermissionDenied
        }
    }
}

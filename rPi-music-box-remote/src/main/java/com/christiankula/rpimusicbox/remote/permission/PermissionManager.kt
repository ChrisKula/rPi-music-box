package com.christiankula.rpimusicbox.remote.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import javax.inject.Inject

const val NEARBY_API_PERMISSION: String = Manifest.permission.ACCESS_COARSE_LOCATION

class PermissionManager @Inject constructor(private val context: Context) {

    fun hasPermissionsForNearby(): Boolean {
        return ContextCompat.checkSelfPermission(context, NEARBY_API_PERMISSION) ==
                PackageManager.PERMISSION_GRANTED
    }
}

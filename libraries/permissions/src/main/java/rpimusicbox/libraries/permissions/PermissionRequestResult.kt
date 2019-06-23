package rpimusicbox.libraries.permissions

sealed class PermissionRequestResult {

    /**
     * Indicate that the permission has been granted
     */
    object PermissionGranted : PermissionRequestResult()

    /**
     * Indicate that the user has denied the permission and you should show a rationale explanation
     * of why the permission is needed
     */
    object ShouldShowRationale : PermissionRequestResult()

    /**
     * Indicate that the user denied the permission and checked the 'Don't ask me again' checkbox
     */
    object PermissionDenied : PermissionRequestResult()

    /**
     * Indicate that an error occurred while processing the grant results from `onRequestPermissionsResult`
     */
    object Error : PermissionRequestResult()
}

package rpimusicbox.features.discovery.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.christiankula.rpimusicbox.androidcommons.extensions.findFragmentByTag
import com.christiankula.rpimusicbox.androidcommons.extensions.goToAppSettings
import com.christiankula.rpimusicbox.androidcommons.extensions.replaceFragment
import dagger.android.AndroidInjection
import rpimusicbox.features.discovery.NEARBY_API_PERMISSION
import rpimusicbox.features.discovery.R
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryState.*
import rpimusicbox.features.discovery.ui.discovering.DiscoveringMusicBoxFragment
import rpimusicbox.features.discovery.ui.found.FoundMusicBoxFragment
import rpimusicbox.features.discovery.ui.start.MusicBoxDiscoveryFailedFragment
import rpimusicbox.features.discovery.ui.start.StartMusicBoxDiscoveryFragment
import rpimusicbox.libraries.permissions.PermissionRequestResult
import rpimusicbox.libraries.permissions.PermissionsManager
import javax.inject.Inject

private const val NEARBY_API_PERMISSION_REQUEST_CODE = 3105

class MusicBoxDiscoveryActivity : AppCompatActivity(),
        StartMusicBoxDiscoveryFragment.InteractionListener,
        DiscoveringMusicBoxFragment.InteractionListener,
        MusicBoxDiscoveryFailedFragment.InteractionListener,
        FoundMusicBoxFragment.InteractionListener {

    @Inject
    lateinit var permissionsManager: PermissionsManager

    @Inject
    lateinit var viewModelFactory: MusicBoxDiscoveryViewModel.Factory

    private lateinit var viewModel: MusicBoxDiscoveryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_box_discovery)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MusicBoxDiscoveryViewModel::class.java)

        viewModel.stateLiveData.observe(this, Observer { state ->
            when (state) {
                is StartMusicBoxDiscovery -> {
                    replaceFragment(R.id.mainContent, StartMusicBoxDiscoveryFragment.newInstance(), StartMusicBoxDiscoveryFragment.TAG)
                }

                is MusicBoxDiscoveryInitiated -> {
                    findFragmentByTag<StartMusicBoxDiscoveryFragment>(StartMusicBoxDiscoveryFragment.TAG)?.setSearchMusicBoxButtonEnabled(false)
                }

                is MusicBoxDiscoveryStarted -> {
                    replaceFragment(R.id.mainContent, DiscoveringMusicBoxFragment.newInstance(), DiscoveringMusicBoxFragment.TAG)
                }

                is MusicBoxDiscoveryFailed -> {
                    replaceFragment(R.id.mainContent, MusicBoxDiscoveryFailedFragment.newInstance(), MusicBoxDiscoveryFailedFragment.TAG)
                }

                is MusicBoxFound -> {
                    replaceFragment(R.id.mainContent, FoundMusicBoxFragment.newInstance(state.musicBox.name), FoundMusicBoxFragment.TAG)
                }

                is MusicBoxLost -> {
                    replaceFragment(R.id.mainContent, DiscoveringMusicBoxFragment.newInstance(state.musicBox.name), DiscoveringMusicBoxFragment.TAG)
                }
            }
        })

        viewModel.permissionRequestLiveData.observe(this, Observer { permission ->
            when (permission) {
                NEARBY_API_PERMISSION -> requestNearbyApiPermission()
            }
        })

        // TODO Handle inter-module navigation
//        viewModel.navigationLiveData.observe(this, Observer {
//            when (it) {
//                is MusicBoxDiscoveryNavigation.NavigateToInstrumentPlayer -> {
//                    startActivity(InstrumentPlayerActivity.newIntent(this, it.musicBox))
//                }
//            }
//        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            NEARBY_API_PERMISSION_REQUEST_CODE -> handleNearbyApiPermissionRequestResult(grantResults)
        }
    }

    override fun onSearchMusicBoxButtonClick() {
        viewModel.onSearchMusicBoxButtonClicked()
    }

    override fun onCancelMusicBoxSearchButtonClick() {
        viewModel.onCancelMusicBoxSearchButtonClicked()
    }

    override fun onRetryMusicBoxSearchButtonClick() {
        viewModel.onRetryMusicBoxSearchButtonClicked()
    }

    override fun onConnectToMusicBoxButtonClick() {
        viewModel.onConnectToMusicBoxButtonClicked()
    }

    private fun requestNearbyApiPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(NEARBY_API_PERMISSION), NEARBY_API_PERMISSION_REQUEST_CODE)
    }

    private fun handleNearbyApiPermissionRequestResult(grantResults: IntArray) {
        return when (permissionsManager.handlePermissionRequestResult(this, NEARBY_API_PERMISSION, grantResults)) {
            PermissionRequestResult.PermissionGranted -> viewModel.onNearbyApiPermissionGranted()
            PermissionRequestResult.ShouldShowRationale -> showNearbyApiPermissionRationaleDialog()
            PermissionRequestResult.PermissionDenied -> showNearbyApiPermissionDeniedDialog()
            PermissionRequestResult.Error -> requestNearbyApiPermission()
        }
    }

    private fun showNearbyApiPermissionRationaleDialog() {
        // The user denied the permission but didn't tick "Never ask again"

        AlertDialog.Builder(this)
                .setTitle(getString(R.string.music_box_discovery_nearby_api_permission_rationale_dialog_title))
                .setMessage(getString(R.string.music_box_discovery_nearby_api_permission_rationale_dialog_message))
                .setPositiveButton(getString(R.string.music_box_discovery_nearby_api_permission_rationale_dialog_positive_button_ask_again)) { _, _ ->
                    requestNearbyApiPermission()
                }
                .show()
    }

    private fun showNearbyApiPermissionDeniedDialog() {
        // The user denied the permission AND ticked "Never ask again"

        AlertDialog.Builder(this)
                .setMessage(getString(R.string.music_box_discovery_nearby_api_permission_denied_dialog_message))
                .setPositiveButton(getString(R.string.music_box_discovery_nearby_api_permission_denied_dialog_positive_button_go_to_app_settings)) { _, _ ->
                    // Redirect user to the app's settings screen
                    goToAppSettings()
                }
                .show()
    }
}

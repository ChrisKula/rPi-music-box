package com.christiankula.rpimusicbox.remote.musicboxdiscovery.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.christiankula.rpimusicbox.remote.R
import com.christiankula.rpimusicbox.remote.extensions.findFragmentByTag
import com.christiankula.rpimusicbox.remote.extensions.goToAppSettings
import com.christiankula.rpimusicbox.remote.extensions.replaceFragment
import com.christiankula.rpimusicbox.remote.musicboxdiscovery.discovering.ui.DiscoveringMusicBoxFragment
import com.christiankula.rpimusicbox.remote.musicboxdiscovery.foundmusicbox.FoundMusicBoxFragment
import com.christiankula.rpimusicbox.remote.musicboxdiscovery.start.ui.MusicBoxDiscoveryFailedFragment
import com.christiankula.rpimusicbox.remote.musicboxdiscovery.start.ui.StartMusicBoxDiscoveryFragment
import com.christiankula.rpimusicbox.remote.permission.NEARBY_API_PERMISSION
import dagger.android.AndroidInjection
import javax.inject.Inject

private const val NEARBY_API_PERMISSION_REQUEST_CODE = 3105

class MusicBoxDiscoveryActivity : AppCompatActivity(),
        StartMusicBoxDiscoveryFragment.InteractionListener,
        DiscoveringMusicBoxFragment.InteractionListener,
        MusicBoxDiscoveryFailedFragment.InteractionListener,
        FoundMusicBoxFragment.InteractionListener {

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
            }
        })

        viewModel.permissionRequestLiveData.observe(this, Observer { permission ->
            when (permission) {
                NEARBY_API_PERMISSION -> requestNearbyApiPermission()
            }
        })
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
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // The user granted the permission, yaay !

            viewModel.onNearbyApiPermissionGranted()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, NEARBY_API_PERMISSION)) {
                // The user denied the permission but didn't tick "Never ask again"

                AlertDialog.Builder(this)
                        .setTitle(getString(R.string.music_box_discovery_nearby_api_permission_rationale_dialog_title))
                        .setMessage(getString(R.string.music_box_discovery_nearby_api_permission_rationale_dialog_message))
                        .setPositiveButton(getString(R.string.music_box_discovery_nearby_api_permission_rationale_dialog_positive_button_ask_again)) { _, _ ->
                            requestNearbyApiPermission()
                        }
                        .show()
            } else {
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
    }
}

package com.christiankula.rpimusicbox.remote.musicboxdiscovery.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.christiankula.rpimusicbox.androidcommons.livedata.SingleLiveEvent
import com.christiankula.rpimusicbox.nearby.Endpoint
import com.christiankula.rpimusicbox.nearby.NearbyUsecase
import com.christiankula.rpimusicbox.nearby.discovery.EndpointDiscoveryInitiated
import com.christiankula.rpimusicbox.nearby.discovery.EndpointDiscoveryStarted
import com.christiankula.rpimusicbox.nearby.discovery.EndpointFound
import com.christiankula.rpimusicbox.nearby.discovery.EndpointLost
import com.christiankula.rpimusicbox.remote.permission.NEARBY_API_PERMISSION
import com.christiankula.rpimusicbox.remote.permission.PermissionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MusicBoxDiscoveryViewModel(private val nearbyUsecase: NearbyUsecase,
                                 private val permissionManager: PermissionManager) : ViewModel() {

    private val _stateLiveData = MutableLiveData<MusicBoxDiscoveryState>()

    val stateLiveData: LiveData<MusicBoxDiscoveryState>
        get() = _stateLiveData

    private val _permissionRequestLiveData = SingleLiveEvent<String>()

    /**
     * Live data that emits String corresponding to a permission to be requested
     */
    val permissionRequestLiveData: LiveData<String>
        get() = _permissionRequestLiveData

    private var observeEndpointDiscoveryDisposable: Disposable? = null

    init {
        _stateLiveData.value = StartMusicBoxDiscovery
    }

    private var foundEndpoint: Endpoint? = null

    fun onSearchMusicBoxButtonClicked() {
        if (permissionManager.hasPermissionsForNearby()) {
            observeEndpoints()
        } else {
            _permissionRequestLiveData.value = NEARBY_API_PERMISSION
        }
    }

    fun onNearbyApiPermissionGranted() {
        observeEndpoints()
    }

    fun onCancelMusicBoxSearchButtonClicked() {
        observeEndpointDiscoveryDisposable?.dispose()

        _stateLiveData.value = StartMusicBoxDiscovery
    }

    fun onRetryMusicBoxSearchButtonClicked() {
        observeEndpoints()
    }

    fun onConnectToMusicBoxButtonClicked() {
        TODO("not implemented")
    }

    private fun observeEndpoints() {
        if (observeEndpointDiscoveryDisposable == null || observeEndpointDiscoveryDisposable?.isDisposed == true) {
            observeEndpointDiscoveryDisposable = nearbyUsecase.observeEndpointDiscovery()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        when (it) {
                            is EndpointDiscoveryInitiated -> _stateLiveData.value = MusicBoxDiscoveryInitiated

                            is EndpointDiscoveryStarted -> _stateLiveData.value = MusicBoxDiscoveryStarted

                            is EndpointFound -> {
                                foundEndpoint = it.endpoint
                                _stateLiveData.value = MusicBoxFound(it.endpoint)
                            }

                            is EndpointLost -> {
                                foundEndpoint?.let { endpoint ->
                                    if (it.id == endpoint.id) {
                                        _stateLiveData.value = MusicBoxLost(endpoint.copy())
                                        foundEndpoint = null
                                    }
                                }
                            }
                        }
                    }, {
                        _stateLiveData.value = MusicBoxDiscoveryFailed
                    })
        }
    }

    override fun onCleared() {
        observeEndpointDiscoveryDisposable?.dispose()
    }

    class Factory @Inject constructor(private val nearbyUsecase: NearbyUsecase,
                                      private val permissionManager: PermissionManager) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MusicBoxDiscoveryViewModel(nearbyUsecase, permissionManager) as T
        }
    }
}

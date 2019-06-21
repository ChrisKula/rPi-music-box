package com.christiankula.rpimusicbox.remote.musicboxdiscovery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.christiankula.rpimusicbox.androidcommons.livedata.SingleLiveEvent
import com.christiankula.rpimusicbox.remote.permission.NEARBY_API_PERMISSION
import com.christiankula.rpimusicbox.remote.permission.PermissionManager
import com.christiankula.rpimusicbox.rxnearby.Endpoint
import com.christiankula.rpimusicbox.rxnearby.RxNearby
import com.christiankula.rpimusicbox.rxnearby.discovery.EndpointDiscoveryInitiated
import com.christiankula.rpimusicbox.rxnearby.discovery.EndpointDiscoveryStarted
import com.christiankula.rpimusicbox.rxnearby.discovery.EndpointFound
import com.christiankula.rpimusicbox.rxnearby.discovery.EndpointLost
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MusicBoxDiscoveryViewModel(private val rxNearby: RxNearby,
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
            observeEndpointDiscoveryDisposable = rxNearby.observeEndpointDiscovery()
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

    class Factory @Inject constructor(private val rxNearby: RxNearby,
                                      private val permissionManager: PermissionManager) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MusicBoxDiscoveryViewModel(rxNearby, permissionManager) as T
        }
    }
}

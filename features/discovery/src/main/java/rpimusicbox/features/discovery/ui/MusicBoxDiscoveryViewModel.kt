package rpimusicbox.features.discovery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import rpimusicbox.features.discovery.DISCOVERY_PERMISSION
import rpimusicbox.features.discovery.models.MusicBox
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryState.*
import rpimusicbox.features.discovery.usecase.DiscoveryUsecase
import rpimusicbox.features.discovery.usecase.MusicBoxDiscoveryEvent
import rpimusicbox.libraries.androidcommons.livedata.SingleLiveEvent
import rpimusicbox.libraries.commons.extensions.exhaustive
import rpimusicbox.libraries.commons.extensions.isNullOrDisposed
import javax.inject.Inject

class MusicBoxDiscoveryViewModel(private val discoveryUsecase: DiscoveryUsecase) : ViewModel() {

    private val _stateLiveData = MutableLiveData<MusicBoxDiscoveryState>()

    val stateLiveData: LiveData<MusicBoxDiscoveryState>
        get() = _stateLiveData

    private val _permissionRequestLiveData = SingleLiveEvent<String>()

    /**
     * Live data that emits String corresponding to a permission to be requested
     */
    val permissionRequestLiveData: LiveData<String>
        get() = _permissionRequestLiveData

    // TODO Handle navigation
//    private val _navigationLiveData = SingleLiveEvent<MusicBoxDiscoveryNavigation>()

    /**
     * Emits events related to navigation from this View
     */
//    val navigationLiveData: LiveData<MusicBoxDiscoveryNavigation>
//    val navigationLiveData: LiveData<MusicBoxDiscoveryNavigation>
//        get() = _navigationLiveData

    private var observeEndpointDiscoveryDisposable: Disposable? = null

    private var foundMusicBox: MusicBox? = null

    init {
        _stateLiveData.value = StartMusicBoxDiscovery
    }

    fun onSearchMusicBoxButtonClicked() {
        if (discoveryUsecase.hasPermissionForDiscovery()) {
            observeEndpoints()
        } else {
            _permissionRequestLiveData.value = DISCOVERY_PERMISSION
        }
    }

    fun onNearbyApiPermissionGranted() {
        observeEndpoints()
    }

    fun onCancelMusicBoxSearchButtonClicked() {
        observeEndpointDiscoveryDisposable?.dispose()

        _stateLiveData.value = MusicBoxDiscoveryCancelled
    }

    fun onRetryMusicBoxSearchButtonClicked() {
        _stateLiveData.value = MusicBoxDiscoveryRetried

        observeEndpoints()
    }

    fun onConnectToMusicBoxButtonClicked() {
        foundMusicBox?.let {
            // TODO Handle navigation
//            _navigationLiveData.value = MusicBoxDiscoveryNavigation.NavigateToInstrumentPlayer(it)
        }
    }

    private fun observeEndpoints() {
        if (observeEndpointDiscoveryDisposable.isNullOrDisposed()) {
            observeEndpointDiscoveryDisposable = discoveryUsecase.observeMusicBoxDiscovery()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ event ->
                        when (event) {
                            MusicBoxDiscoveryEvent.DiscoveryInitiated -> _stateLiveData.value = MusicBoxDiscoveryInitiated

                            MusicBoxDiscoveryEvent.DiscoveryStarted -> _stateLiveData.value = MusicBoxDiscoveryStarted

                            is MusicBoxDiscoveryEvent.MusicBoxFound -> {
                                foundMusicBox = event.foundMusicBox.also {
                                    _stateLiveData.value = MusicBoxFound(it)
                                }
                            }

                            is MusicBoxDiscoveryEvent.MusicBoxLost -> {
                                if (event.lostMusicBoxId == foundMusicBox?.id) {
                                    _stateLiveData.value = MusicBoxLost(foundMusicBox!!.copy())
                                    foundMusicBox = null
                                }

                                Unit
                            }
                        }.exhaustive
                    }, {
                        _stateLiveData.value = MusicBoxDiscoveryFailed
                    })
        }
    }

    override fun onCleared() {
        observeEndpointDiscoveryDisposable?.dispose()
    }

    class Factory @Inject constructor(private val discoveryUsecase: DiscoveryUsecase) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MusicBoxDiscoveryViewModel(discoveryUsecase) as T
        }
    }
}

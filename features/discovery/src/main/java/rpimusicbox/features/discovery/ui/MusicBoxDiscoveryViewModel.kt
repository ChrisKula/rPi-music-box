package rpimusicbox.features.discovery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import rpimusicbox.libraries.androidcommons.livedata.SingleLiveEvent
import com.christiankula.rpimusicbox.rxnearby.RxNearby
import com.christiankula.rpimusicbox.rxnearby.discovery.DiscoveryInitiated
import com.christiankula.rpimusicbox.rxnearby.discovery.DiscoveryStarted
import com.christiankula.rpimusicbox.rxnearby.discovery.EndpointFound
import com.christiankula.rpimusicbox.rxnearby.discovery.EndpointLost
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import rpimusicbox.features.discovery.NEARBY_API_PERMISSION
import rpimusicbox.features.discovery.models.MusicBox
import rpimusicbox.features.discovery.models.fromEndpoint
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryState.*
import rpimusicbox.libraries.permissions.PermissionsManager
import javax.inject.Inject

class MusicBoxDiscoveryViewModel(private val rxNearby: RxNearby,
                                 private val permissionManager: PermissionsManager) : ViewModel() {

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

    init {
        _stateLiveData.value = StartMusicBoxDiscovery
    }

    private var foundMusicBox: MusicBox? = null

    fun onSearchMusicBoxButtonClicked() {
        if (permissionManager.hasPermission(NEARBY_API_PERMISSION)) {
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
        foundMusicBox?.let {
            // TODO Handle navigation
//            _navigationLiveData.value = MusicBoxDiscoveryNavigation.NavigateToInstrumentPlayer(it)
        }
    }

    private fun observeEndpoints() {
        if (observeEndpointDiscoveryDisposable == null || observeEndpointDiscoveryDisposable?.isDisposed == true) {
            observeEndpointDiscoveryDisposable = rxNearby.observeDiscovery()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        when (it) {
                            is DiscoveryInitiated -> _stateLiveData.value = MusicBoxDiscoveryInitiated

                            is DiscoveryStarted -> _stateLiveData.value = MusicBoxDiscoveryStarted

                            is EndpointFound -> {
                                fromEndpoint(it.endpoint).also { musicBox ->
                                    foundMusicBox = musicBox
                                    _stateLiveData.value = MusicBoxFound(musicBox)
                                }
                            }

                            is EndpointLost -> {
                                foundMusicBox?.let { musicBox ->
                                    if (it.id == musicBox.id) {
                                        _stateLiveData.value = MusicBoxLost(musicBox.copy())
                                        foundMusicBox = null
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
                                      private val permissionsManager: PermissionsManager) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MusicBoxDiscoveryViewModel(rxNearby, permissionsManager) as T
        }
    }
}

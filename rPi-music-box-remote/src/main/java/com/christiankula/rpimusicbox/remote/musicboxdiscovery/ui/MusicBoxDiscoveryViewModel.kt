package com.christiankula.rpimusicbox.remote.musicboxdiscovery.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.christiankula.rpimusicbox.remote.nearby.Endpoint
import com.christiankula.rpimusicbox.remote.nearby.EndpointDiscoveryStarted
import com.christiankula.rpimusicbox.remote.nearby.EndpointFound
import com.christiankula.rpimusicbox.remote.nearby.NearbyUsecase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class MusicBoxDiscoveryViewModel(private val nearbyUsecase: NearbyUsecase) : ViewModel() {

    private val _stateLiveData = MutableLiveData<MusicBoxDiscoveryState>()

    val stateLiveData: LiveData<MusicBoxDiscoveryState>
        get() = _stateLiveData

    private val disposables = CompositeDisposable()

    init {
        _stateLiveData.value = StartMusicBoxDiscovery
    }

    private var foundEndpoint: Endpoint? = null

    fun onSearchMusicBoxButtonClicked() {
        disposables += nearbyUsecase.observeEndpointDiscovery()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it) {
                        is EndpointDiscoveryStarted -> {
                            _stateLiveData.value = MusicBoxDiscoveryStarted
                        }

                        is EndpointFound -> {
                            foundEndpoint = it.endpoint
                            _stateLiveData.value = MusicBoxFound(it.endpoint)
                        }
                    }
                }, {
                    _stateLiveData.value = MusicBoxDiscoveryFailed
                })
    }

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }

    class Factory @Inject constructor(private val nearbyUsecase: NearbyUsecase) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MusicBoxDiscoveryViewModel(nearbyUsecase) as T
        }
    }
}

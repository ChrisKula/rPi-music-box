package rpimusicbox.features.instrumentplayer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import rpimusicbox.features.instrumentplayer.ui.InstrumentPlayerState.*
import rpimusicbox.libraries.commons.extensions.disposeBy
import rpimusicbox.libraries.commons.randomanimal.generateRandomAnimalName
import rpimusicbox.libraries.rxnearby.RxNearby
import rpimusicbox.libraries.rxnearby.discovery.connection.*
import javax.inject.Inject

class InstrumentPlayerViewModel(foundMusicBox: MusicBox, rxNearby: RxNearby) : ViewModel() {

    private val _stateLiveData = MutableLiveData<InstrumentPlayerState>()

    val stateLiveData: LiveData<InstrumentPlayerState>
        get() = _stateLiveData

    private val disposables = CompositeDisposable()

    private val serverClientRelation = ServerClientRelation(foundMusicBox, generateRandomAnimalName())

    init {
        rxNearby.observeDiscoveryConnection(foundMusicBox.id, serverClientRelation.clientName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ event ->
                    return@subscribe when (event) {
                        is ConnectionRequested -> _stateLiveData.value = ConnectionToMusicBoxRequested(serverClientRelation)

                        is ConnectionInitiated -> _stateLiveData.value = ConnectionToMusicBoxInitiated(serverClientRelation)

                        is ConnectionApproved -> _stateLiveData.value = ConnectionToMusicBoxApproved(serverClientRelation)

                        is ConnectionRejected -> _stateLiveData.value = ConnectionToMusicBoxRejected(serverClientRelation)

                        is ConnectionError -> _stateLiveData.value = ConnectionToMusicBoxError(serverClientRelation)

                        is EndpointDisconnected -> _stateLiveData.value = MusicBoxDisconnected(serverClientRelation)

                        is PayloadReceived -> {
                            // Not used
                        }
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                    TODO("Implement error handling")
                })
                .disposeBy(disposables)

        _stateLiveData.value = IdleState(serverClientRelation)
    }

    override fun onCleared() {
        disposables.clear()
    }

    class Factory @Inject constructor(private val rxNearby: RxNearby) : ViewModelProvider.Factory {

        private lateinit var foundMusicBox: MusicBox

        fun init(foundMusicBox: MusicBox) {
            this.foundMusicBox = foundMusicBox
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return InstrumentPlayerViewModel(foundMusicBox, rxNearby) as T
        }
    }
}

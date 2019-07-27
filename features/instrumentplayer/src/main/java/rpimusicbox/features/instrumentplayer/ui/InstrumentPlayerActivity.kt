package rpimusicbox.features.instrumentplayer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_instrument_player.*
import rpimusicbox.core.actions.instrumentplayer.EXTRA_MUSIC_BOX
import rpimusicbox.core.actions.instrumentplayer.MusicBoxArgs
import rpimusicbox.features.instrumentplayer.R
import rpimusicbox.features.instrumentplayer.ui.InstrumentPlayerState.*
import javax.inject.Inject


class InstrumentPlayerActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: InstrumentPlayerViewModel.Factory

    private lateinit var viewModel: InstrumentPlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrument_player)

        requireArguments()

//        viewModelFactory.init(getFoundMusicBoxFromIntent())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(InstrumentPlayerViewModel::class.java)

        viewModel.stateLiveData.observe(this, Observer {
            return@Observer when (it) {
                is IdleState -> connectionStatusBar.clear()

                is ConnectionToMusicBoxRequested ->
                    connectionStatusBar.setConnectionRequested(it.serverClientRelation.serverMusicBox.name)

                is ConnectionToMusicBoxInitiated ->
                    connectionStatusBar.setConnectionInitiated(it.serverClientRelation.serverMusicBox.name)

                is ConnectionToMusicBoxApproved ->
                    connectionStatusBar.setConnectionApproved(it.serverClientRelation.clientName)

                is ConnectionToMusicBoxRejected -> {
                    TODO()
                }

                is ConnectionToMusicBoxError -> {
                    TODO()
                }

                is MusicBoxDisconnected -> TODO()

                null -> {
                    // Do nothing
                }
            }
        })
    }

    private fun requireArguments() {
        if (getFoundMusicBoxFromIntent() == null) {
            throw IllegalStateException("Can't start InstrumentPlayerActivity without providing a MusicBox")
        }
    }

    private fun getFoundMusicBoxFromIntent() = intent.getParcelableExtra<MusicBoxArgs>(EXTRA_MUSIC_BOX)
}

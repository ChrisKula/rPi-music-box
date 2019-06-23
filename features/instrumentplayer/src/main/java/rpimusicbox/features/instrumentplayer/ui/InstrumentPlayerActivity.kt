package rpimusicbox.features.instrumentplayer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_instrument_player.*
import rpimusicbox.features.instrumentplayer.R
import rpimusicbox.features.instrumentplayer.ui.InstrumentPlayerState.*
import javax.inject.Inject

private const val FOUND_MUSIC_BOX_EXTRA_KEY = "FOUND_MUSIC_BOX"

class InstrumentPlayerActivity : AppCompatActivity() {

    companion object {
        /**
         * Create an Intent to navigate to this Activity
         *
         * @param originContext the Context that originated the Intent
         * @param foundMusicBox the [MusicBox] that has been previously found that will be connected to,
         * as the connection happens in this [InstrumentPlayerActivity]
         */
        fun newIntent(originContext: Context, foundMusicBox: MusicBox): Intent {
            return Intent(originContext, InstrumentPlayerActivity::class.java)
                    .apply { putExtra(FOUND_MUSIC_BOX_EXTRA_KEY, foundMusicBox) }
        }
    }

    @Inject
    lateinit var viewModelFactory: InstrumentPlayerViewModel.Factory

    private lateinit var viewModel: InstrumentPlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrument_player)

        requireArguments()

        viewModelFactory.init(getFoundMusicBoxFromIntent())
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

    private fun getFoundMusicBoxFromIntent() = intent.getParcelableExtra<MusicBox>(FOUND_MUSIC_BOX_EXTRA_KEY)
}

package com.christiankula.rpimusicbox.remote.injection

import com.christiankula.rpimusicbox.remote.features.instrumentplayer.injection.InstrumentPlayerModule
import com.christiankula.rpimusicbox.remote.features.instrumentplayer.ui.InstrumentPlayerActivity
import com.christiankula.rpimusicbox.remote.musicboxdiscovery.injection.MusicBoxDiscoveryActivityModule
import com.christiankula.rpimusicbox.remote.musicboxdiscovery.ui.MusicBoxDiscoveryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [MusicBoxDiscoveryActivityModule::class])
    abstract fun bindMusicBoxDiscoveryActivity(): MusicBoxDiscoveryActivity

    @ContributesAndroidInjector(modules = [InstrumentPlayerModule::class])
    abstract fun bindInstrumentPlayerActivity(): InstrumentPlayerActivity
}

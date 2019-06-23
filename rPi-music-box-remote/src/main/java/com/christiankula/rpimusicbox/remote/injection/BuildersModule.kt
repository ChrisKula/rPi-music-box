package com.christiankula.rpimusicbox.remote.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import rpimusicbox.features.discovery.injection.MusicBoxDiscoveryActivityModule
import rpimusicbox.features.discovery.ui.MusicBoxDiscoveryActivity

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [MusicBoxDiscoveryActivityModule::class])
    abstract fun bindMusicBoxDiscoveryActivity(): MusicBoxDiscoveryActivity
}

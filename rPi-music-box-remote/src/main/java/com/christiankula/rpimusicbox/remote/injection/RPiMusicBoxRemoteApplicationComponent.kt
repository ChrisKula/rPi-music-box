package com.christiankula.rpimusicbox.remote.injection

import com.christiankula.rpimusicbox.remote.RPiMusicBoxRemoteApplication
import com.christiankula.rpimusicbox.remote.nearby.injection.NearbyModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    BuildersModule::class,
    RPiMusicBoxRemoteApplicationModule::class,
    NearbyModule::class
])
interface RPiMusicBoxRemoteApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(rPiMusicBoxRemoteApplication: RPiMusicBoxRemoteApplication): Builder

        fun build(): RPiMusicBoxRemoteApplicationComponent
    }

    fun inject(rPiMusicBoxRemoteApplication: RPiMusicBoxRemoteApplication)
}

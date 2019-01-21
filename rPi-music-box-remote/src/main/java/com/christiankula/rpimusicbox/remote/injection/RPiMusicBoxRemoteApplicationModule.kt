package com.christiankula.rpimusicbox.remote.injection

import android.content.Context
import com.christiankula.rpimusicbox.remote.RPiMusicBoxRemoteApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RPiMusicBoxRemoteApplicationModule {

    @Provides
    @Singleton
    fun provideContext(rPiMusicBoxRemoteApplication: RPiMusicBoxRemoteApplication): Context {
        return rPiMusicBoxRemoteApplication.applicationContext
    }
}
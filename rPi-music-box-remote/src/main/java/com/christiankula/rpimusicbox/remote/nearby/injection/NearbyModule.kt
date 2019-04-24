package com.christiankula.rpimusicbox.remote.nearby.injection

import android.content.Context
import com.christiankula.rpimusicbox.nearby.NearbyUsecase
import dagger.Module
import dagger.Provides

@Module
class NearbyModule {

    @Provides
    internal fun provideNearbyUsecase(context: Context): NearbyUsecase {
        return NearbyUsecase(context)
    }
}

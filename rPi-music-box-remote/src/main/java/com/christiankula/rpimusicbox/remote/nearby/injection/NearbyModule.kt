package com.christiankula.rpimusicbox.remote.nearby.injection

import android.content.Context
import com.christiankula.rpimusicbox.rxnearby.RxNearby
import dagger.Module
import dagger.Provides

@Module
class NearbyModule {

    @Provides
    internal fun provideRxNearby(context: Context): RxNearby {
        return RxNearby(context)
    }
}

package com.christiankula.rpimusicbox.remote.nearby.injection

import android.content.Context
import com.christiankula.rpimusicbox.nearby.NearbyUsecase
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.ConnectionsClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NearbyModule {

    @Provides
    @Singleton
    internal fun provideConnectionsClient(context: Context): ConnectionsClient {
        return Nearby.getConnectionsClient(context)
    }

    @Provides
    internal fun provideNearbyUsecase(connectionsClient: ConnectionsClient): NearbyUsecase {
        return NearbyUsecase(connectionsClient)
    }
}
package rpimusicbox.features.discovery.injection

import android.content.Context
import rpimusicbox.libraries.rxnearby.RxNearby
import dagger.Module
import dagger.Provides
import rpimusicbox.libraries.permissions.PermissionsManager

@Module
class MusicBoxDiscoveryActivityModule {

    @Provides
    fun provideRxNearby(context: Context): RxNearby {
        return RxNearby(context)
    }

    @Provides
    fun providePermissionsManager(context: Context): PermissionsManager {
        return PermissionsManager(context)
    }
}

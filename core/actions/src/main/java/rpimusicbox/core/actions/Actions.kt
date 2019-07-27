package rpimusicbox.core.actions

import android.content.Intent
import rpimusicbox.core.actions.instrumentplayer.EXTRA_MUSIC_BOX
import rpimusicbox.core.actions.instrumentplayer.MusicBoxArgs

/**
 * Object holding all methods to create [Intent] to open all app's Activities
 *
 * When creating an Activity, be sure to add the corresponding Intent creating method here and to
 * specify the **same action name** here and in AndroidManifest.xml when declaring an `intent-filter` for the
 * Activity
 *
 * **Example**
 *
 * _In feature's module AndroidManifest.xml_
 *
 * ```
 * <activity android:name="FeatureActivity>
 *     <intent-filter>
 *         <action android:name="action.feature.open" />
 *         <category android:name="android.intent.category.DEFAULT" />
 *     </intent-filter>
 *  </activity>
 * ```
 *
 * _In Actions.kt_
 *
 * `fun openFeatureIntent(): Intent = Intent("action.feature.open")`
 * Note the action name **`action.feature.open`** in both files
 */
object Actions {

    /**
     * Create an Intent opening the Discovery feature
     */
    fun openDiscoveryIntent(): Intent = Intent("action.discovery.open")

    /**
     * Create an Intent opening the Instrument Player feature
     */
    fun openInstrumentPlayerIntent(musicBoxArgs: MusicBoxArgs): Intent =
            Intent("action.instrument_player.open").putExtra(EXTRA_MUSIC_BOX, musicBoxArgs)
}

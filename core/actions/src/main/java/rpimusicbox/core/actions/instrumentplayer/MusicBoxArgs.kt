package rpimusicbox.core.actions.instrumentplayer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val EXTRA_MUSIC_BOX = "feature.instrumentplayer.extra.musicbox"

@Parcelize
data class MusicBoxArgs(val id: String, val name:String, val serviceId:String) : Parcelable

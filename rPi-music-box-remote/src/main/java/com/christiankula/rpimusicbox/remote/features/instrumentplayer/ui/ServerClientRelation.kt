package com.christiankula.rpimusicbox.remote.features.instrumentplayer.ui

import com.christiankula.rpimusicbox.remote.models.MusicBox

/**
 * A convenient class representing a relation between a server and a client
 */
data class ServerClientRelation(val serverMusicBox: MusicBox, val clientName: String)

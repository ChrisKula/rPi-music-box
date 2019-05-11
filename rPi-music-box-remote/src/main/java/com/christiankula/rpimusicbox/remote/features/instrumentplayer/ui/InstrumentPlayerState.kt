package com.christiankula.rpimusicbox.remote.features.instrumentplayer.ui

sealed class InstrumentPlayerState

data class IdleState(val serverClientRelation: ServerClientRelation) : InstrumentPlayerState()

data class ConnectionToMusicBoxRequested(val serverClientRelation: ServerClientRelation) : InstrumentPlayerState()
data class ConnectionToMusicBoxInitiated(val serverClientRelation: ServerClientRelation) : InstrumentPlayerState()

data class ConnectionToMusicBoxApproved(val serverClientRelation: ServerClientRelation) : InstrumentPlayerState()
data class ConnectionToMusicBoxRejected(val serverClientRelation: ServerClientRelation) : InstrumentPlayerState()

data class ConnectionToMusicBoxError(val serverClientRelation: ServerClientRelation) : InstrumentPlayerState()

data class MusicBoxDisconnected(val serverClientRelation: ServerClientRelation) : InstrumentPlayerState()

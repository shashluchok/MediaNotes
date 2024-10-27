package com.shashluchok.medianotes.presentation.screen.medianotes

import com.shashluchok.medianotes.presentation.screen.AbsViewModel
import kotlinx.coroutines.flow.MutableStateFlow

internal class MediaNotesViewModel : AbsViewModel<MediaNotesViewModel.State>() {
    data class State(
        val stub: Boolean = false
    )

    override val mutableStateFlow: MutableStateFlow<State> = MutableStateFlow(
        State()
    )
}

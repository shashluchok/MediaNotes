package com.shashluchok.medianotes

import androidx.compose.runtime.Composable
import com.shashluchok.medianotes.presentation.screen.medianotes.MediaNotesScreen
import com.shashluchok.medianotes.presentation.theme.MediaNotesTheme

@Composable
fun MediaNotesApp() {
    MediaNotesTheme {
        MediaNotesScreen()
    }
}

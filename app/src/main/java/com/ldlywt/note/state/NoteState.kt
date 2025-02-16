package com.ldlywt.note.state

import com.ldlywt.note.bean.NoteShowBean

data class NoteState(
    val notes: List<NoteShowBean> = emptyList(),
    val title: String = "",
    val content: String = "",
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val editingNote: NoteShowBean? = null,
)

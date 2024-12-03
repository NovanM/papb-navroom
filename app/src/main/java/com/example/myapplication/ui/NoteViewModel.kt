package com.example.myapplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Note
import com.example.myapplication.data.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel(){
    val allNotes: StateFlow<List<Note>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    suspend fun getNoteById(id: Int?): Note? {
        // Should run in a coroutine or use LiveData to observe
        return id?.let { repository.getNoteById(it) }
    }

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}

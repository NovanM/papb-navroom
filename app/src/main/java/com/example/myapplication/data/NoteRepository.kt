package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }


    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

}

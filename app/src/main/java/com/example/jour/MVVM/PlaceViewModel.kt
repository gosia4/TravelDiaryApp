package com.example.jour.MVVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceViewModel(application: Application): AndroidViewModel(application){
    val allEntries: LiveData<List<Place>>
    val repository: PlaceRepository

    init {
        val dao = PlaceDatabase.getDatabase(application).getEntitiesDao()
        repository= PlaceRepository(dao)
        allEntries=repository.allNotes
    }

    fun deleteNote(note: Place) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }
    fun updateNote(note: Place) = viewModelScope.launch(Dispatchers.IO){
        repository.update(note)
    }
    fun addNote(note: Place) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }
}
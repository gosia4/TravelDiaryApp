package com.example.jour.MVVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlaceRepository
    val allEntries: LiveData<List<Place>>
    var addedByAddEditNoteActivity: Boolean = false

    init {
        val dao = PlaceDatabase.getDatabase(application).getEntitiesDao()
        repository= PlaceRepository(dao)
        allEntries=repository.allNotes
    }

    fun addNote(place: Place) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(place)
    }

    fun updateNote(place: Place) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(place)
    }

    fun deleteNote(place: Place) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(place)
    }

    fun getPlaceByTitle(title: String): LiveData<Place> {
        return repository.getPlaceByTitle(title)
    }

    fun getAllPlaces(): LiveData<List<Place>> {
        return repository.getAllPlaces()
    }

    fun sortAlphabetically(): LiveData<List<Place>> {
        return repository.getAllEntriesAlphabetically()
    }

    fun sortByDate(): LiveData<List<Place>> {
        return repository.getAllEntriesByDate()
    }

}

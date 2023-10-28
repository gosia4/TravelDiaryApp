package com.example.jour.MVVM

import androidx.lifecycle.LiveData

class PlaceRepository(private val placeDao: PlaceDao) {

    val allNotes: LiveData<List<Place>> = placeDao.getAllEntries()


    suspend fun insert(note: Place){
        placeDao.insert(note)
    }

    suspend fun delete(note: Place){
        placeDao.delete(note)
    }

    suspend fun update(note: Place){
        placeDao.update(note)
    }
}
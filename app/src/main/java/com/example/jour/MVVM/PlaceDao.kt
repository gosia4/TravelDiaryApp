package com.example.jour.MVVM

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Place): Long

    @Update
    fun update(note: Place)

    @Delete
    fun delete(note: Place)

    @Query(value = "Select * from PlaceTable ")
    fun getAllEntries(): LiveData<List<Place>>

    @Query("SELECT * FROM PlaceTable WHERE Name = :title")
    fun getPlaceByTitle(title: String): LiveData<Place>

    @Query("SELECT * FROM PlaceTable ORDER BY Name ASC")
    fun getAllEntriesAlphabetically(): LiveData<List<Place>>

    @Query("SELECT * FROM PlaceTable ORDER BY date ASC")
    fun getAllEntriesByDate(): LiveData<List<Place>>

}
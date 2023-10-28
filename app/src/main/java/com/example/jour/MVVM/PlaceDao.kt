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
}
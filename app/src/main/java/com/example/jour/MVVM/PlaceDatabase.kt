package com.example.jour.MVVM

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jour.DateTypeConverter
import com.example.jour.imgConverter.ImageConverter

@Database(entities = arrayOf(Place::class), version = 1, exportSchema = false)
@TypeConverters(ImageConverter::class, DateTypeConverter::class)
abstract class PlaceDatabase: RoomDatabase() {
    abstract fun getEntitiesDao(): PlaceDao

    companion object{

        @Volatile
        private var INSTANCE: PlaceDatabase?=null

        fun getDatabase(context: Context): PlaceDatabase {
            return INSTANCE ?: synchronized(this ){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    PlaceDatabase::class.java,
                    "place_database"
                ).fallbackToDestructiveMigration().
                build()
                INSTANCE =instance
                instance
            }
        }

    }
}
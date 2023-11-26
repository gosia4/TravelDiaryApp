package com.example.jour.MVVM

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "PlaceTable")
class Place(
    @ColumnInfo(name = "Name") var jourTitle:String,
    @ColumnInfo(name = "description") var jourDescription:String,
//    @ColumnInfo(name = "date") val jourDate:Date,
    @ColumnInfo(name = "date") var jourDate:String,
    @ColumnInfo(name = "rating") val rating:Int,
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB) val jourImage: Bitmap?,
    @ColumnInfo(name = "latitude") val latitude: Double = 0.0,
    @ColumnInfo(name = "longitude") val longitude: Double = 0.0
) {
    @PrimaryKey(autoGenerate = true)var id=0
}
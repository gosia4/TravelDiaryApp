package com.example.jour.MVVM

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "PlaceTable")
class Place(
    @ColumnInfo(name = "Name") var jourTitle:String,
    @ColumnInfo(name = "description") var jourDescription:String,
    @ColumnInfo(name = "date") var jourDate:String,
    @ColumnInfo(name = "rating") val rating:Int,
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB) val jourImage: Bitmap?,
    @ColumnInfo(name = "latitude") val latitude: Double?,
    @ColumnInfo(name = "longitude") val longitude: Double?,
    @ColumnInfo(name = "added_via_map") val addedViaMap: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)var id=0
}
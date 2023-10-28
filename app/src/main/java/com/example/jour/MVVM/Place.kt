package com.example.jour.MVVM

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "PlaceTable")
class Place(
    @ColumnInfo(name = "Name") val jourTitle:String,
    @ColumnInfo(name = "description") val jourDescription:String,
//    @ColumnInfo(name = "date") val jourDate:Date,
    @ColumnInfo(name = "date") val jourDate:String,
    @ColumnInfo(name = "rating") val rating:Int,
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB) val jourImage: Bitmap?
) {
    @PrimaryKey(autoGenerate = true)var id=0
}
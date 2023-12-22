package com.example.dishdash.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "favorite_resto")
@Parcelize
data class RestoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    var name : String = "",

    @ColumnInfo(name = "photoUrl")
    var photoUrl : String? = null,

    @ColumnInfo(name = "rating")
    var rating : String? = null,

    @ColumnInfo(name = "favorite")
    var login: String? ="",

) : Parcelable

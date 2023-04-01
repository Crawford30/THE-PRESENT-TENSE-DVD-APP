package com.dvds.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "present_tense_audio_dvd_table")
data class AudioDVD(

    @PrimaryKey
    @ColumnInfo(name = "audio_dvd_name")
    val audioDVDName: String,
    @ColumnInfo(name = "audio_dvd_path")
    val audioDVDPath: String,

    @ColumnInfo(name = "creation_date")
    val creationDate: String,

){
    //primary key
//    @PrimaryKey(autoGenerate = true)
//    var id:Int = CURRENT_PRESENT_TENSE_SONG_ID
}
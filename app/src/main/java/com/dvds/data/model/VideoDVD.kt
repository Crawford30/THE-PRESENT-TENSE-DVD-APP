package com.dvds.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "present_tense_video_dvd_table")
data class VideoDVD(

    @PrimaryKey
    @ColumnInfo(name = "video_dvd_name")
    val videoDVDName: String,
    @ColumnInfo(name = "video_dvd_path")
    val videoDVDPath: String,

    @ColumnInfo(name = "creation_date")
    val creationDate: String,

    ){
    //primary key
//    @PrimaryKey(autoGenerate = true)
//    var id:Int = CURRENT_PRESENT_TENSE_SONG_ID
}
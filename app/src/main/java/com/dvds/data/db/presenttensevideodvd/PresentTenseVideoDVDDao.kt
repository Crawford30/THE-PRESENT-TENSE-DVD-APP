package com.dvds.data.db.presenttensevideodvd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvds.data.model.VideoDVD

@Dao
interface PresentTenseVideoDVDDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //whenever there is conflict, replace
    fun insertPresentTenseVideoDVD(audioDVD: MutableList<VideoDVD>)

    @Query("SELECT * FROM present_tense_video_dvd_table ORDER BY video_dvd_name + 0 ASC")
    fun getPresentTenseVideoDVDLists(): List<VideoDVD>

    @Query("DELETE FROM present_tense_video_dvd_table")
    fun deleteAllPresentTenseVideoDVD()

}
package com.dvds.data.db.presenttenseaudiodvd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvds.data.model.AudioDVD

@Dao
interface PresentTenseAudioDVDDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //whenever there is conflict, replace
    fun insertPresentTenseAudioDVD(audioDVD: MutableList<AudioDVD>)

    //@Query("SELECT * FROM Login WHERE Username =:username")
    @Query("SELECT * FROM present_tense_audio_dvd_table ORDER BY audio_dvd_name + 0 ASC")
    fun getPresentTenseAudioDVDLists(): List<AudioDVD>

    @Query("DELETE FROM present_tense_audio_dvd_table")
    fun deleteAllPresentTenseAudioDVD()

}
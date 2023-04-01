package com.dvds.data.db.presenttenseaudiodvd

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dvds.data.model.AudioDVD

@Database(
    entities = [AudioDVD::class],
//    autoMigrations = [
//        AutoMigration (from = 1, to = 2)
//    ],
    version = 1,
    exportSchema = true
)


abstract class PresentTenseAudioDVDDatabase: RoomDatabase() {

    //Create instance of interface
    abstract fun presentTenseAudioDVDDao(): PresentTenseAudioDVDDao

    companion object {
        @Volatile
        private var instance: PresentTenseAudioDVDDatabase? =
            null //@Volatile all threads will have immediate action on it

        private val LOCK = Any() //use to mean no threads is currently doing the same thing

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            //check if instance is already initialized

            instance ?: buildDatabase(context).also {
                instance = it
            } //whatever is returned, we set the instance equal to it
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PresentTenseAudioDVDDatabase::class.java, "present_tense_audio_dvd.db"
        ).allowMainThreadQueries()
            .build()

    }

}
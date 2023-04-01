package com.dvds.data.db.presenttensevideodvd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dvds.data.model.VideoDVD

@Database(
    entities = [VideoDVD::class],
//    autoMigrations = [
//        AutoMigration (from = 1, to = 2)
//    ],
    version = 1,
    exportSchema = true
)


abstract class PresentTenseVideoDVDDatabase: RoomDatabase() {

    //Create instance of interface
    abstract fun presentTenseVideoDVDDao(): PresentTenseVideoDVDDao

    companion object {
        @Volatile
        private var instance: PresentTenseVideoDVDDatabase? =
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
            PresentTenseVideoDVDDatabase::class.java, "present_tense_video_dvd.db"
        ).allowMainThreadQueries()
            .build()

    }

}
package com.example.mobileassignment

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Flashcard::class], version = 1)
abstract class MainDatabase: RoomDatabase() {

    abstract fun flashcardDao(): FlashCardsDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase {
            if (INSTANCE == null) {
                synchronized(MainDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MainDatabase::class.java,
                        name = "flashcardsDB"
                    ).build()
                }

            }
            return INSTANCE!!
        }
    }
}
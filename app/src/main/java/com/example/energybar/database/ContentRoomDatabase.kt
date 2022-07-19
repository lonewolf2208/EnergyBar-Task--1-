package com.example.energybar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.energybar.model.Content
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Content::class), version = 1, exportSchema = false)
abstract class ContentRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): ContentDAO

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao()

                    // Delete all content here.
                    wordDao.deleteAll()
                    var word = mutableListOf(
                        Content(1, 25, "#e84c3d"),
                        Content(26, 50,"#f0a30b"),
                        Content(51, 75,"#0099cc"),
                        Content(76, 100,"#c1e859")
                    )
                    wordDao.insert(word)
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: ContentRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ContentRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContentRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
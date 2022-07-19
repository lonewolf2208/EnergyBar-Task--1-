package com.example.energybar.repo

import androidx.annotation.WorkerThread
import com.example.energybar.database.ContentDAO
import com.example.energybar.model.Content

class ContentRepo(private val wordDao: ContentDAO) {

    // Room executes all queries on a` separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords :kotlinx.coroutines.flow.Flow<List<Content>> = wordDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: MutableList<Content>) {
        wordDao.insert(word)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delteAll()
    {
        wordDao.deleteAll()
    }
}

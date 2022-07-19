package com.example.energybar

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.energybar.model.Content
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDAO {

    @Query("SELECT * FROM content_table ORDER BY start ASC")
    fun getAlphabetizedWords(): Flow<List<Content>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data:MutableList<Content>)

    @Query("DELETE FROM content_table")
    suspend fun deleteAll()


}
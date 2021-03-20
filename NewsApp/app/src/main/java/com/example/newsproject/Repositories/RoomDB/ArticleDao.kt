package com.example.newsproject.Repositories.RoomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ArticleDao {

    @Query("SELECT * from Article_table ")
    fun getAllTemps(): LiveData<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: ArticleEntity)

    @Query("DELETE FROM Article_table")
    suspend fun deleteAll()
}
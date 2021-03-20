package com.example.newsproject.Repositories

import androidx.lifecycle.LiveData
import com.example.newsproject.Repositories.RoomDB.ArticleDao
import com.example.newsproject.Repositories.RoomDB.ArticleEntity


class ArticleDBRepository(private val articleDao: ArticleDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allArticles: LiveData<List<ArticleEntity>> = articleDao.getAllTemps()

    suspend fun insert(article: ArticleEntity) {
        articleDao.insert(article)
    }
}
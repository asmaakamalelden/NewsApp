package com.example.newsproject.Repositories.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = arrayOf(ArticleEntity::class), version = 1, exportSchema = false)
public abstract class ArticleRoomDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    private class ArticleDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var articleDao = database.articleDao()

                    // Delete all content here.
//                    tempDao.deleteAll()

                    // Add sample words.
//                    var temp = WeatherTemp(null,"Hello","12/3/5")
//                    tempDao.insert(temp) // Add sample temp.
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ArticleRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ArticleRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, ArticleRoomDatabase::class.java, "article_database"
                )
                    .addCallback(ArticleDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
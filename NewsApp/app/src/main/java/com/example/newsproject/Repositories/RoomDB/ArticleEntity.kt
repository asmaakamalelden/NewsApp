package com.example.newsproject.Repositories.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Article_table")
class ArticleEntity(@PrimaryKey(autoGenerate = true) var id:Long?=null,
                    @ColumnInfo(name = "title") val title: String
                    , @ColumnInfo(name = "description") var description: String
                    , @ColumnInfo(name = "publishedAt") var publishedAt: String
                    , @ColumnInfo(name = "url") var url: String
                    , @ColumnInfo(name = "urlToImage") var urlToImage: String
                    , @ColumnInfo(name = "name") var sourceName: String
)
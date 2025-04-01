package com.bvn.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bvn.newsapp.data.local.NewsDao
import com.bvn.newsapp.data.local.NewsTypeConverter
import com.bvn.newsapp.domain.model.Article

@Database(entities = [Article::class], version = 2)
@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDao: NewsDao

}
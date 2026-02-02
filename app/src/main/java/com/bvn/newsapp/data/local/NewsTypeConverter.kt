package com.bvn.newsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bvn.booksappcompose.data.local.entity.NewsSourceEntity
import javax.inject.Inject
import javax.inject.Singleton

@ProvidedTypeConverter
class NewsTypeConverter @Inject constructor() {

    @TypeConverter
    fun sourceToString(source: NewsSourceEntity): String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(source: String): NewsSourceEntity {
        return source.split(",").let {
            NewsSourceEntity(id = it[0], name = it[1])
        }
    }
}
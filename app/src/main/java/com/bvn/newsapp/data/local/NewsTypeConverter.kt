package com.bvn.newsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bvn.newsapp.domain.model.Source
import javax.inject.Inject
import javax.inject.Singleton

@ProvidedTypeConverter
class NewsTypeConverter @Inject constructor() {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(source: String): Source {
        return source.split(",").let { sourceArray ->
            Source(id = sourceArray[0], name = sourceArray[1])
        }
    }
}
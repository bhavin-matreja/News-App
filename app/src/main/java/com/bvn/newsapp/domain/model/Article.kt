package com.bvn.newsapp.domain.model


import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Article(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded val source: Source, // this is an object, database we can only save premative datatypes
    val title: String,
    @PrimaryKey val url: String,
    val urlToImage: String
): Parcelable
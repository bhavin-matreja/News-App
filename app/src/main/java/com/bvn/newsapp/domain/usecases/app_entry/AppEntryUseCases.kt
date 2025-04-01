package com.bvn.newsapp.domain.usecases.app_entry

import com.bvn.newsapp.domain.usecases.app_entry.ReadAppEntry
import com.bvn.newsapp.domain.usecases.app_entry.SaveAppEntry

data class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry
)

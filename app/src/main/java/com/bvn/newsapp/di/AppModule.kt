package com.bvn.newsapp.di

import android.app.Application
import androidx.room.Room
import com.bvn.newsapp.data.local.NewsDao
import com.bvn.newsapp.data.local.NewsDatabase
import com.bvn.newsapp.data.local.NewsTypeConverter
import com.bvn.newsapp.data.manager.LocalUserManagerImpl
import com.bvn.newsapp.data.remote.NewsApi
import com.bvn.newsapp.data.remote.repository.NewsRepositoryImpl
import com.bvn.newsapp.domain.manager.LocalUserManager
import com.bvn.newsapp.domain.repository.NewsRepository
import com.bvn.newsapp.domain.usecases.app_entry.AppEntryUseCases
import com.bvn.newsapp.domain.usecases.app_entry.ReadAppEntry
import com.bvn.newsapp.domain.usecases.app_entry.SaveAppEntry
import com.bvn.newsapp.domain.usecases.news.DeleteArticleUseCase
import com.bvn.newsapp.domain.usecases.news.GetArticleUseCase
import com.bvn.newsapp.domain.usecases.news.GetNewsUseCase
import com.bvn.newsapp.domain.usecases.news.NewsUseCases
import com.bvn.newsapp.domain.usecases.news.SearchNewsUseCase
import com.bvn.newsapp.domain.usecases.news.SelectArticlesUseCase
import com.bvn.newsapp.domain.usecases.news.UpsertArticleUseCase
import com.bvn.newsapp.util.Constants.BASE_URL
import com.bvn.newsapp.util.Constants.NEWS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager {
        return LocalUserManagerImpl(application)
    }

    @Singleton
    @Provides
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsApi(okHttpClient: OkHttpClient): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun providesNewsRepository(newsApi: NewsApi, newsDao: NewsDao): NewsRepository =
        NewsRepositoryImpl(newsApi = newsApi, newsDao = newsDao)

    @Singleton
    @Provides
    fun provideNewsUseCases(newsRepository: NewsRepository, newsDao: NewsDao): NewsUseCases {
        return NewsUseCases(
            getNewsUseCase = GetNewsUseCase(newsRepository),
            searchNewsUseCase = SearchNewsUseCase((newsRepository)),
            deleteArticleUseCase = DeleteArticleUseCase(newsRepository),
            upsertArticleUseCase = UpsertArticleUseCase(newsRepository),
            selectArticlesUseCase = SelectArticlesUseCase(newsRepository),
            getArticleUseCase = GetArticleUseCase(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application,
        newsTypeConverter: NewsTypeConverter
    ): NewsDatabase {
        return Room.databaseBuilder(
            application,
            NewsDatabase::class.java,
            NEWS_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao = newsDatabase.newsDao
}
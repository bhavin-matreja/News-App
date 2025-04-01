package com.bvn.newsapp.data.remote

import android.graphics.pdf.LoadParams
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bvn.newsapp.data.remote.NewsApi
import com.bvn.newsapp.domain.model.Article

class NewsPagingSouce(
    private val newsApi: NewsApi,
    private val sources: String
) : PagingSource<Int, Article>() {
// Int is page and data is Article
    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.getNews(sources = sources, page = page)
            totalNewsCount += newsResponse.articles.size
            val articles = newsResponse.articles.distinctBy { it.title } //Remove duplicates

            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        /* called when we referesh page or load data for first time
        anchorPosition is latest accessed page in the list
         */
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}
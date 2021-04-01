package raum.muchbeer.flowktx.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import raum.muchbeer.flowktx.api.UnsplashService
import raum.muchbeer.flowktx.model.UnsplashPhoto

private const val UNSPLASH_STARTING_PAGE_INDEX = 1
class UnsplashPagingSource(
    private val service: UnsplashService,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = service.searchPhoto(query, page, params.loadSize)
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

}
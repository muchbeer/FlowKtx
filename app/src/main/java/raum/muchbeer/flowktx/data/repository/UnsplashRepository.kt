package raum.muchbeer.flowktx.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.flowktx.api.UnsplashService
import raum.muchbeer.flowktx.data.paging.UnsplashPagingSource
import raum.muchbeer.flowktx.model.UnsplashPhoto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository
    @Inject constructor(
        private val service: UnsplashService
    ) {

    fun getSearchResultStream(query: String): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { UnsplashPagingSource(service, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}
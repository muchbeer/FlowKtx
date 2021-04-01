package raum.muchbeer.flowktx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.flowktx.data.repository.UnsplashRepository
import raum.muchbeer.flowktx.model.UnsplashPhoto
import javax.inject.Inject

@HiltViewModel
class PhotoVM
@Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel(){

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<UnsplashPhoto>>? = null

    fun searchPictures(queryString: String): Flow<PagingData<UnsplashPhoto>> {
        currentQueryValue = queryString
        val newResult: Flow<PagingData<UnsplashPhoto>> =
            repository.getSearchResultStream(queryString).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}
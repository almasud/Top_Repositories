package com.github.almasud.top_repositories.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.github.almasud.top_repositories.data.GithubRepository
import com.github.almasud.top_repositories.model.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * ViewModel for the [SearchViewModel] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
class SearchViewModel(
    private val repository: GithubRepository
) : ViewModel() {
    private val currentQuery = MutableStateFlow(DEFAULT_QUERY)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow: Flow<PagingData<UiModel>> =
        currentQuery.flatMapLatest { queryString ->
            repository.getSearchResultStream(queryString)
                .map { pagingData -> pagingData.map { UiModel.RepoItem(it) } }
                .map {
                    it.insertSeparators { before, after ->
                        if (after == null) {
                            // we're at the end of the list
                            return@insertSeparators null
                        }

                        if (before == null) {
                            // we're at the beginning of the list
                            return@insertSeparators UiModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                        }
                        // check between 2 items
                        if (before.roundedStarCount > after.roundedStarCount) {
                            if (after.roundedStarCount >= 1) {
                                UiModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
                            } else {
                                UiModel.SeparatorItem("< 10.000+ stars")
                            }
                        } else {
                            // no separator
                            null
                        }
                    }
                }.cachedIn(viewModelScope)
        }

    fun searchRepo(queryString: String) {
        currentQuery.update { queryString }
    }

    companion object {
        private const val TAG = "SearchViewModel"
        private const val DEFAULT_QUERY = "Android"
    }
}

sealed class UiModel {
    data class RepoItem(val repo: Repo) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}

private val UiModel.RepoItem.roundedStarCount: Int
    get() = this.repo.stars / 10_000

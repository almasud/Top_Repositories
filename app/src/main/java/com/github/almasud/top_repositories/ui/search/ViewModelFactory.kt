package com.github.almasud.top_repositories.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.almasud.top_repositories.data.GithubRepository
import  com.github.almasud.top_repositories.ui.search.SearchViewModel

/**
 * Factory for ViewModels
 */
class ViewModelFactory(
    private val repository: GithubRepository
) : ViewModelProvider.Factory {
    @Override
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

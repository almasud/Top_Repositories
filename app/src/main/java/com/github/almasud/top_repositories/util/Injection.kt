package com.github.almasud.top_repositories.util

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.github.almasud.top_repositories.api.GithubService
import com.github.almasud.top_repositories.data.GithubRepository
import com.github.almasud.top_repositories.db.RepoDatabase
import com.github.almasud.top_repositories.ui.search.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [GithubRepository] based on the [GithubService] and a
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(context: Context) =
        GithubRepository(GithubService.create(), RepoDatabase.getInstance(context))

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context) =
        ViewModelFactory(provideGithubRepository(context))
}

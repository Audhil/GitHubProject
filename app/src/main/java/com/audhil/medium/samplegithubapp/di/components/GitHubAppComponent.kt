package com.audhil.medium.samplegithubapp.di.components

import com.audhil.medium.samplegithubapp.GitHubDelegate
import com.audhil.medium.samplegithubapp.di.modules.*
import com.audhil.medium.samplegithubapp.repository.BaseRepository
import com.audhil.medium.samplegithubapp.ui.base.BaseViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (ApplicationModule::class),
        (APIModule::class),
        (SharedPreferenceModule::class),
        (DataBaseModule::class),
        (RepositoryModule::class)
    ]
)
interface GitHubAppComponent {
    fun inject(into: GitHubDelegate)
    fun inject(into: BaseRepository)
    fun inject(into: BaseViewModel)
}
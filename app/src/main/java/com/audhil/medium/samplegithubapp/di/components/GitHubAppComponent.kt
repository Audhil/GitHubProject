package com.audhil.medium.samplegithubapp.di.components

import com.audhil.medium.samplegithubapp.SampleGitHubApp
import com.audhil.medium.samplegithubapp.di.modules.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (ApplicationModule::class),
        (APIModule::class)
    ]
)
interface GitHubAppComponent {
    fun inject(into: SampleGitHubApp)
}
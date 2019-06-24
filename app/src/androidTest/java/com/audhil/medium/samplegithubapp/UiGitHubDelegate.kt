package com.audhil.medium.samplegithubapp

import com.audhil.medium.samplegithubapp.di.TestApplicationModule
import com.audhil.medium.samplegithubapp.di.components.DaggerGitHubAppComponent
import com.audhil.medium.samplegithubapp.di.components.GitHubAppComponent

open class UiGitHubDelegate : GitHubDelegate() {

    override fun getAppComponent(): GitHubAppComponent {
        return DaggerGitHubAppComponent.builder()
            .applicationModule(TestApplicationModule(this))
            .build()
    }
}
package com.audhil.medium.samplegithubapp.ui.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.annotation.LayoutRes

abstract class BaseLifeCycleActivity<B : ViewDataBinding, T : AndroidViewModel> : BaseActivity() {

    abstract val viewModelClass: Class<T>

    protected val viewModel: T by lazy { ViewModelProviders.of(this).get(viewModelClass) }

    lateinit var viewDataBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    //  data binding variable
    abstract fun getBindingVariable(): Int

    //  layout id
    @LayoutRes
    abstract fun getLayoutId(): Int
}
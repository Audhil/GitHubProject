package com.audhil.medium.samplegithubapp.ui.launch

import android.os.Bundle
import com.audhil.medium.samplegithubapp.R
import com.audhil.medium.samplegithubapp.databinding.ActivityLaunchBinding
import com.audhil.medium.samplegithubapp.ui.base.BaseLifeCycleActivity
import com.audhil.medium.samplegithubapp.util.showDataAlertDialog
import com.audhil.medium.samplegithubapp.util.showVLog

class LaunchActivity : BaseLifeCycleActivity<ActivityLaunchBinding, LaunchViewModel>() {

    override val viewModelClass: Class<LaunchViewModel>
        get() = LaunchViewModel::class.java

    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.activity_launch

    override fun internetAvailable() =
        showVLog("internetAvailable")

    override fun internetUnAvailable() =
        showVLog("internetUnAvailable")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDataAlertDialog {
            goBtnClickListener {
                dialog?.dismiss()
            }

        }.show()
    }
}
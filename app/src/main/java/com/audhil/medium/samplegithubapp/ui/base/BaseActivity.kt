package com.audhil.medium.samplegithubapp.ui.base

import android.content.*
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.audhil.medium.samplegithubapp.util.isNetworkConnected

abstract class BaseActivity : AppCompatActivity() {

    //  internet callbacks
    abstract fun internetAvailable()

    abstract fun internetUnAvailable()

    //  bool
    var isInternetUnAvailableBool = false

    //  internet listener
    private var internetListener: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (this@BaseActivity.isNetworkConnected() && isInternetUnAvailableBool) {
                internetAvailable()
                isInternetUnAvailableBool = false
            } else {
                if (!this@BaseActivity.isNetworkConnected()) {
                    internetUnAvailable()
                    isInternetUnAvailableBool = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(internetListener, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(internetListener)
    }
}
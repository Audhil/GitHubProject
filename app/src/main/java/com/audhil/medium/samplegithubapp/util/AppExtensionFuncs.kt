package com.audhil.medium.samplegithubapp.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.audhil.medium.samplegithubapp.GitHubDelegate
import com.audhil.medium.samplegithubapp.ui.dialog.DataDialogHelper

//  show logs
fun Any.showVLog(log: String) = GLog.v(this::class.java.simpleName, log)

fun Any.showELog(log: String) = GLog.e(this::class.java.simpleName, log)

fun Any.showDLog(log: String) = GLog.d(this::class.java.simpleName, log)

fun Any.showILog(log: String) = GLog.i(this::class.java.simpleName, log)

fun Any.showWLog(log: String) = GLog.w(this::class.java.simpleName, log)

var toast: Toast? = null

//  show toast
fun Any.showToast(context: Context? = GitHubDelegate.INSTANCE, duration: Int = Toast.LENGTH_SHORT) {
    toast?.cancel()
    toast = when {
        this is String ->
            Toast.makeText(context, this, duration)
        this is Int ->
            Toast.makeText(context, this, duration)
        else ->
            Toast.makeText(context, ConstantsUtil.INVALID_INPUT, duration)
    }
    toast?.show()
}

//  is network connected
fun Context.isNetworkConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNet: NetworkInfo?
    return if (cm != null) {
        activeNet = cm.activeNetworkInfo
        activeNet != null && activeNet.isConnected
    } else
        false
}

//  dialog helper
inline fun Activity.showDataAlertDialog(func: DataDialogHelper.() -> Unit): AlertDialog =
    DataDialogHelper(this).apply {
        func()
    }.create()

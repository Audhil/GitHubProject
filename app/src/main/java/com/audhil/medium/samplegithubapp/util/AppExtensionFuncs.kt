package com.audhil.medium.samplegithubapp.util

import android.content.Context
import android.widget.Toast
import com.audhil.medium.samplegithubapp.SampleGitHubApp

//  show logs
fun Any.showVLog(log: String) = GLog.v(this::class.java.simpleName, log)

fun Any.showELog(log: String) = GLog.e(this::class.java.simpleName, log)

fun Any.showDLog(log: String) = GLog.d(this::class.java.simpleName, log)

fun Any.showILog(log: String) = GLog.i(this::class.java.simpleName, log)

fun Any.showWLog(log: String) = GLog.w(this::class.java.simpleName, log)

var toast: Toast? = null

//  show toast
fun Any.showToast(context: Context? = SampleGitHubApp.sINSTANCE, duration: Int = Toast.LENGTH_SHORT) {
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

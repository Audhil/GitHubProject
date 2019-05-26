package com.audhil.medium.samplegithubapp.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
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

//  pref
fun Any.writeToPref(
    tag: String,
    prefEditor: SharedPreferences.Editor = GitHubDelegate.INSTANCE.sPreferencesEditor
) {
    when (this) {
        is String -> {
            prefEditor.putString(tag, this)
            showVLog("STRING WRITTEN TO PREF : key: $tag, value: $this")
        }

        is Int -> {
            prefEditor.putInt(tag, this)
            showVLog("INT WRITTEN TO PREF : key: $tag, value: $this")
        }

        is Long -> {
            prefEditor.putLong(tag, this)
            showVLog("LONG WRITTEN TO PREF : key: $tag, value: $this")
        }

        is Boolean -> {
            prefEditor.putBoolean(tag, this)
            showVLog("BOOLEAN WRITTEN TO PREF : key: $tag, value: $this")
        }

        is Float -> {
            prefEditor.putFloat(tag, this)
            showVLog("FLOAT WRITTEN TO PREF : key: $tag, value: $this")
        }

        else -> throw IllegalArgumentException("INVALID_INPUT_SHARED_PREFERENCE")
    }
    prefEditor.commit()
}

fun String.readStringFromPref(sPreferences: SharedPreferences = GitHubDelegate.INSTANCE.sPreferences): String =
    sPreferences.getString(this, ConstantsUtil.EMPTY)

fun String.readIntFromPref(sPreferences: SharedPreferences = GitHubDelegate.INSTANCE.sPreferences): Int =
    sPreferences.getInt(this, 0)

fun String.readLongFromPref(sPreferences: SharedPreferences = GitHubDelegate.INSTANCE.sPreferences): Long =
    sPreferences.getLong(this, 0L)

fun String.readBooleanFromPref(sPreferences: SharedPreferences = GitHubDelegate.INSTANCE.sPreferences): Boolean =
    sPreferences.getBoolean(this, false)

fun String.readFloatFromPref(sPreferences: SharedPreferences = GitHubDelegate.INSTANCE.sPreferences): Float =
    sPreferences.getFloat(this, 0f)

//  callbacks
typealias BiCallBack<T, V> = (T, V) -> Unit


//  dialog helper
inline fun Activity.showDataAlertDialog(func: DataDialogHelper.() -> Unit): AlertDialog =
    DataDialogHelper(this).apply {
        func()
    }.create()

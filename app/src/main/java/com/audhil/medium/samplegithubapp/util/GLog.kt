package com.audhil.medium.samplegithubapp.util

import android.util.Log

object GLog {
    val DEBUG_BOOL = true

    fun v(tag: String, msg: String) {
        if (DEBUG_BOOL) {
            Log.v(ConstantsUtil.DASH + tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (DEBUG_BOOL) {
            Log.e(ConstantsUtil.DASH + tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (DEBUG_BOOL) {
            Log.d(ConstantsUtil.DASH + tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (DEBUG_BOOL) {
            Log.i(ConstantsUtil.DASH + tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (DEBUG_BOOL) {
            Log.w(ConstantsUtil.DASH + tag, msg)
        }
    }
}
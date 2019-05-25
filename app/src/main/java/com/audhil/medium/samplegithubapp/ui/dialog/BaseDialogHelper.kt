package com.audhil.medium.samplegithubapp.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.audhil.medium.samplegithubapp.GitHubDelegate

abstract class BaseDialogHelper {

    abstract val dialogView: View?
    abstract val builder: AlertDialog.Builder
    abstract val dialogHelper: BaseDialogHelper

    //  required bools
    open var cancelable: Boolean = true
    open var isBackGroundTransparent: Boolean = true

    //  dialog
    open var dialog: AlertDialog? = null

    //  dialog create
    open fun create(): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()
        //  very much needed for customised dialogs
        if (isBackGroundTransparent)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog!!
    }

    //  show listener
    open fun onShowListener(func: () -> Unit) =
        dialog?.setOnShowListener {
            func()
        }

    //  dismiss listener
    open fun onDismissListener(func: () -> Unit) =
        dialog?.setOnDismissListener {
            func()
        }

    //  cancel listener
    open fun onCancelListener(func: () -> Unit): AlertDialog.Builder? =
        builder.setOnCancelListener {
            func()
        }
}
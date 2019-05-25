package com.audhil.medium.samplegithubapp.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.audhil.medium.samplegithubapp.R

class DataDialogHelper(context: Context) : BaseDialogHelper() {

    override val dialogHelper: BaseDialogHelper = this

    //  dialog view
    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.dialog_data, null)
    }

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)

    val ownerEditText by lazy {
        dialogView.findViewById<EditText>(R.id.owner_etext_view)
    }

    val repoEditText by lazy {
        dialogView.findViewById<EditText>(R.id.repo_etext_view)
    }

    val goBtn by lazy {
        dialogView.findViewById<Button>(R.id.go_btn)
    }

    fun goBtnClickListener(func: () -> Unit) =
        with(goBtn) {
            setOnClickListener {
                func.invoke()
            }
        }
}
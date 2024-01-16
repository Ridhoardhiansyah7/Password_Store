package com.onedive.passwordstore.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * This function is used to display a default dialog with a title, message, and 2 confirmation buttons
 */
fun showDialog(
    context: Context, title: String, message: String,
    positiveBtnText: String, negativeBtnText: String,
    positiveBtnClick: () -> Unit,
    neutralBtnClick: (() -> Unit?)?
) {

    val dialog = MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton(positiveBtnText) { dialog, _ ->
            positiveBtnClick()
            dialog.dismiss()
        }
        .setNeutralButton(negativeBtnText) { dialog, _ ->
            if (neutralBtnClick != null){
                neutralBtnClick()
            }else{
                dialog.dismiss()
            }
        }

    dialog.show()

}
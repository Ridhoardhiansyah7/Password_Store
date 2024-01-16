package com.onedive.passwordstore.utils

import android.content.Context
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration

internal fun showToast( context:Context, message:String, @Duration duration: Int ){
    Toast.makeText(context,message,duration).show()
}


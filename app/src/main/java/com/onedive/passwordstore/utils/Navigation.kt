package com.onedive.passwordstore.utils

import android.content.Context
import android.content.Intent

/**
 * this function is used to navigate to other activities by carrying single data
 * @author Ridh
 */
internal fun toAnotherActivity(key: String, value:String, from: Context, to:Class<*>) {
    val intent = Intent(from, to)
    intent.putExtra(key, value)
    from.startActivity(intent)
}
package com.onedive.passwordstore.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

private var sharedPreferences: SharedPreferences? = null
private var encryptedSharedPreferences:SharedPreferences? = null

/**
 * this function is used to get the default sharedPreference
 * @author Ridh
 */
internal fun getDefaultSharedPreference(context:Context) : SharedPreferences {
    if (sharedPreferences == null){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }
    return sharedPreferences!!
}

/**
 * This function is used to get encrypted preferences
 * @author Ridh
 */
internal fun getEncryptedSharedPreferences(context: Context) : SharedPreferences {
    if (encryptedSharedPreferences == null){

        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypt_shared",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    }
    return encryptedSharedPreferences!!
}
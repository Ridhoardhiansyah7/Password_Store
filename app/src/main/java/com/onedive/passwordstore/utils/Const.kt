package com.onedive.passwordstore.utils

/**
 * This class contains immutable values, which are used by this application
 * @author Ridh
 */
object Const {

    init { System.loadLibrary("native-lib") }

    // Base Url to check for updates
    const val UPDATE_BASE_URL = "https://api.jsonbin.io/v3/"


    // MainActivity / ListByTagActivity -> DetailPasswordActivity
    const val EXTRA_DETAIL_KEY = "ExtraDetail"

    //MainActivity -> ListByTagActivity
    const val EXTRA_LIST_BY_TAG = "ExtraTag"

    // MainActivity / DetailPasswordActivity  -> EditPasswordActivity
    const val EXTRA_EDIT = "ExtraEdit"



    const val LOCK_APP_KEY_PREFERENCE = "lockPreference"

    const val LOCK_DETAIL_KEY_PREFERENCE = "lockDetailPreference"


    //DEVELOPER CONTACT NUM
    const val DEVELOPER_CONTACT_NUMBER = "62895323021645"


    @Deprecated(
        message = "It is not safe to use this method because the encryption key stored in C++ " +
                "is not encrypted. So please use the encryption shared preference",
        replaceWith = ReplaceWith(expression = "encryptedSharedPreferences(context)"),
        level = DeprecationLevel.ERROR
    )
    /**
     * Default encryption key, implemented using native c++,
     * you may be able to change the way the key is stored, one of them is using encrypt shared preference
     * @return default key in native c++
     */
    external fun getNativeEncryptKey(): String

    /**
     *
     * @return JSON BIN ID in native c++
     */
    external fun getUpdateJsonBinId(): String

    /**
     *
     * @return X-MASTER-KEY for JSON BIN in native c++
     */
    external fun getApiSecretKey(): String

}
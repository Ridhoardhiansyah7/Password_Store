package com.onedive.passwordstore.utils;

/**
 * This class contains immutable values, which are used by this application
 * @author Ridh
 */
public final class Const {

    static {
        System.loadLibrary("native-lib");
    }

    // Base Url to check for updates
    public static final String UPDATE_BASE_URL = "https://api.jsonbin.io/v3/";

    //NAVIGATION KEY DATA THAT CONTAINS VALUES SENT TO OTHER ACTIVITIES

    // MainActivity / ListByTagActivity -> DetailPasswordActivity
    public static final String EXTRA_DETAIL_KEY = "ExtraDetail";

    //MainActivity -> ListByTagActivity
    public static final String EXTRA_LIST_BY_TAG = "ExtraTag";

    // MainActivity / DetailPasswordActivity  -> EditPasswordActivity
    public static final String EXTRA_EDIT = "ExtraEdit";


    //PREFERENCE key data is used to store application settings preferences
    public static final String LOCK_APP_KEY_PREFERENCE = "lockPreference";
    //public static final String ENCRYPT_KEY_PREFERENCE = "encryptPreference";

    //DEVELOPER CONTACT NUM
    public static final String DEVELOPER_CONTACT_NUMBER = "62895323021645";

    /**
     * Default encryption key, implemented using native c++,
     * you may be able to change the way the key is stored, one of them is using encrypt shared preference
     * @return default key in native c++
     */
    public static native String getNativeEncryptKey();

    /**
     *
     * @return JSON BIN ID in native c++
     */
    public static native String getUpdateJsonBinId();

    /**
     *
     * @return X-MASTER-KEY for JSON BIN in native c++
     */

    public static native String getApiSecretKey();

}

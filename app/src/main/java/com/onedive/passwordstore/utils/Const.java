package com.onedive.passwordstore.utils;

/**
 * This class contains immutable values, which are used by this application
 * @author Ridh
 */
public final class Const {

    //NAVIGATION KEY DATA THAT CONTAINS VALUES SENT TO OTHER ACTIVITIES

    // MainActivity / ListByTagActivity -> DetailPasswordActivity
    public static final String EXTRA_DETAIL_KEY = "ExtraDetail";

    //MainActivity -> ListByTagActivity
    public static final String EXTRA_LIST_BY_TAG = "ExtraTag";

    //DetailPasswordActivity -> EditPasswordActivity
    public static final String EXTRA_EDIT = "ExtraEdit";


    //PREFERENCE key data is used to store application settings preferences
    public static final String LOCK_APP_KEY_PREFERENCE = "lockPreference";
    public static final String ENCRYPT_KEY_PREFERENCE = "encryptPreference";


}

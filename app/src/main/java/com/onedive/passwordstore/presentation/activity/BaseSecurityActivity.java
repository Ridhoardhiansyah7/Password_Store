package com.onedive.passwordstore.presentation.activity;

import static com.onedive.passwordstore.utils.SharedPreferenceKt.getDefaultSharedPreference;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.onedive.passwordstore.R;
import com.onedive.passwordstore.utils.Const;

/**
 * This class is used if in settings,
 * the application lock feature is activated and the device has an active biometric password/password,
 * then this class will display a dialog that is used to confirm this device password.
 *
 * @param <B> viewBinding to use
 * @see BaseActivity
 */
public abstract class BaseSecurityActivity<B extends ViewBinding> extends BaseActivity<B> {

    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        lockAppAndDetailNavigation();
    }

    /**
     * Check whether the preference with the key Const.LOCK_APP_KEY_PREFERENCE exists / does not exist
     * if there is then run the startResultLauncher() method otherwise run the deviceSecurityIsNotAvailable() method
     *
     * @see Const
     */
    private void lockAppAndDetailNavigation() {

        if (getDefaultSharedPreference(this).getBoolean(Const.LOCK_APP_KEY_PREFERENCE,false)) {
            startResultLauncher();
        }else {
             deviceSecurityIsNotAvailable();
        }

    }

    /**
     * This method is used to check whether the device has an active password / biometric
     * If there is, then display the password confirmation dialog
     *
     * @see KeyguardManager
     */
    private void startResultLauncher() {
        if (keyguardManager.isKeyguardSecure()){
            Intent intent = keyguardManager.createConfirmDeviceCredentialIntent(getString(R.string.enter_device_password), getString(R.string.enter_device_password_desc));
            activityResultLauncher.launch(intent);
        }else{
            deviceSecurityIsNotAvailable();
        }
    }


    /**
     * if the result of the password entered is wrong then close this activity if not,
     * then run the deviceSecurityIsAvailable() method
     */
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() != BaseActivity.RESULT_OK) {
                    finish();
                } else {
                    deviceSecurityIsAvailable();
                }
            }
    );

    /**
     * abstract method that is executed if the device has a password
     */
    protected abstract void deviceSecurityIsAvailable();

    /**
     * abstract method that is executed if the device does not have a password
     */
    protected abstract void deviceSecurityIsNotAvailable();
}

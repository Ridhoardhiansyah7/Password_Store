package com.onedive.passwordstore.uiLayer.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import androidx.viewbinding.ViewBinding;

import com.onedive.passwordstore.R;
import com.onedive.passwordstore.utils.Const;

public abstract class BaseSecurityActivity<B extends ViewBinding> extends BaseActivity<B> {

    private SharedPreferences sharedPreferences;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        lockAppAndDetailNavigation();

    }

    private void lockAppAndDetailNavigation() {

        if (sharedPreferences.getBoolean(Const.LOCK_APP_KEY_PREFERENCE, false)) {
            startResultLauncher();
        }else {
             deviceSecurityIsNotAvailable();
        }

    }


    private void startResultLauncher() {
        if (keyguardManager.isKeyguardSecure()){
            Intent intent = keyguardManager.createConfirmDeviceCredentialIntent(
                    getString(R.string.enter_device_password),
                    getString(R.string.enter_device_password_desc)
            );
            activityResultLauncher.launch(intent);
        }else{
            deviceSecurityIsNotAvailable();
        }
    }


    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            o -> {
                if (o.getResultCode() != BaseActivity.RESULT_OK) {
                    finish();
                } else {
                    deviceSecurityIsAvailable();
                }
            }
    );

    protected abstract void deviceSecurityIsAvailable();

    protected abstract void deviceSecurityIsNotAvailable();
}

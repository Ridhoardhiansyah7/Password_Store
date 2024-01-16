package com.onedive.passwordstore.presentation.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.ERROR_NO_BIOMETRICS
import androidx.biometric.BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.viewbinding.ViewBinding
import com.onedive.passwordstore.R
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.sharedPreference
import com.onedive.passwordstore.utils.showToast

/**
 * This class is an abstract class that is used to request a password/biometric when an activity is
 * running
 * @see BaseActivity
 * @see BiometricPrompt
 * @see PromptInfo
 * @param B View Binding to use
 */
abstract class BaseSecurityActivity<B : ViewBinding> : BaseActivity<B>() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var biometricPromptBuilder: PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Check whether the app lock setting is on
        if (sharedPreference(this).getBoolean(Const.LOCK_APP_KEY_PREFERENCE,false)) {
            showConfirmDialogWithBiometricPassword()
        } else {
            noAvailablePasswordOrBiometricPasswordInThisDevice()
        }

    }

    @SuppressLint("SwitchIntDef")
    @Suppress("DEPRECATION")
    // Show password/biometric confirmation request dialog
    private fun showConfirmDialogWithBiometricPassword() {

        biometricPromptBuilder = PromptInfo.Builder().apply {
            val auth = BIOMETRIC_STRONG or DEVICE_CREDENTIAL

            setTitle(getString(R.string.app_name))
            setDescription(getString(R.string.enter_device_password_desc))
            setConfirmationRequired(false)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) setDeviceCredentialAllowed(true)
            else setAllowedAuthenticators(auth)



        }.build()

        biometricPrompt = BiometricPrompt(this,authCallback)
        biometricPrompt.authenticate(biometricPromptBuilder)
    }

    // Biometric callback
    private val authCallback = object : BiometricPrompt.AuthenticationCallback() {


        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            /*
             If the error is not because the device does not have an active password / biometric,
              then display a toast, de authenticate, and close the activity
            */
            if (errorCode == ERROR_NO_BIOMETRICS || errorCode == ERROR_NO_DEVICE_CREDENTIAL) {
                confirmPasswordOrBiometricPasswordIsSuccessfully()
            } else {
                biometricPrompt.cancelAuthentication()
                showToast(
                    context = applicationContext,
                    message = "error with code $errorCode${
                        if (errString.isNotEmpty()) " with message $errString"  
                        else ""
                    }" ,
                    duration = Toast.LENGTH_SHORT
                )
                finish()
            }
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            /*
             If authentication is successful,
             then execute the abstract function confirmPasswordOrBiometricPasswordIsSuccessfully()
             */
            confirmPasswordOrBiometricPasswordIsSuccessfully()
        }

        override fun onAuthenticationFailed() {
            // Display a toast with a message that you are not the original owner of this device
            showToast(
                context = applicationContext,
                message = "It looks like you are not the original owner of this device",
                duration = Toast.LENGTH_SHORT
            )
        }

    }


    /**
     * This function is performed when the user's confirmation with password / biometric
     * has been successful and if this device not available password
     */
    abstract fun confirmPasswordOrBiometricPasswordIsSuccessfully()

    /**
     * This function is performed when checking has been completed but the device
     * does not have an active password / biometric
     */
    abstract fun noAvailablePasswordOrBiometricPasswordInThisDevice()

}


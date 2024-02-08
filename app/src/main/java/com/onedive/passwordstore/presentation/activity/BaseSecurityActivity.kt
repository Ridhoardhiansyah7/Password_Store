package com.onedive.passwordstore.presentation.activity

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.ERROR_NO_BIOMETRICS
import androidx.biometric.BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.viewbinding.ViewBinding
import com.onedive.passwordstore.R
import com.onedive.passwordstore.utils.showToast

/**
 * This class is an abstract class that is used to request a password/biometric when an activity is
 * running
 * @see BaseActivity
 * @see KeyguardManager
 * @see BiometricPrompt
 * @see PromptInfo
 * @param B View Binding to use
 */
abstract class BaseSecurityActivity<B : ViewBinding> : BaseActivity<B>() {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var biometricPromptBuilder: PromptInfo


    @Suppress("DEPRECATION")
    protected fun showConfirmDialogWithAvailablePasswordOrBiometricPassword() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            biometricPromptBuilder = PromptInfo.Builder().apply {
                setTitle(getString(R.string.app_name))
                setDescription(getString(R.string.enter_device_password_desc))
                setConfirmationRequired(false)
                setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            }.build()

            biometricPrompt = BiometricPrompt(this, authCallback)
            biometricPrompt.authenticate(biometricPromptBuilder)

        } else {

            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            val intent = keyguardManager.createConfirmDeviceCredentialIntent(
                getString(R.string.app_name),
                getString(R.string.enter_device_password_desc)
            )

            val resultLauncher = registerForActivityResult(ActivityResultContracts
                .StartActivityForResult()) {

                if (it.resultCode == RESULT_OK) {
                    confirmPasswordOrBiometricPasswordIsSuccessfully()
                } else {
                    // If the back button is pressed or another action occurs that "Keyguard"
                    // fails to execute, complete the activity
                    finish()
                }

            }
            resultLauncher.launch(intent)

        }

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
                    }",
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


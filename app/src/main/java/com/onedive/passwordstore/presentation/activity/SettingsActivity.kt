package com.onedive.passwordstore.presentation.activity

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.onedive.passwordstore.R
import com.onedive.passwordstore.databinding.ActivitySettingsBinding
import com.onedive.passwordstore.presentation.fragment.SettingsFragment
import com.onedive.passwordstore.presentation.receiver.DownloadReceiver
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.sharedPreference
import de.raphaelebner.roomdatabasebackup.core.RoomBackup

class SettingsActivity : BaseSecurityActivity<ActivitySettingsBinding>() {

    private lateinit var transaction : FragmentTransaction
    lateinit var roomBackup: RoomBackup
    private lateinit var downloadReceiver : DownloadReceiver

    @SuppressLint("UnspecifiedRegisterReceiverFlag", "InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySettingsBinding.inflate(layoutInflater))


        // Check to see the password details on the lock, if it is locked then show the keyguard
        if (
            sharedPreference(this).getBoolean(Const.LOCK_APP_KEY_PREFERENCE,false) &&
            sharedPreference(this).getBoolean(Const.LOCK_DETAIL_KEY_PREFERENCE,false)
        ){
            confirmPasswordOrBiometricPasswordIsSuccessfully()
        }  else if (sharedPreference(this).getBoolean(Const.LOCK_DETAIL_KEY_PREFERENCE,false)){
            showConfirmDialogWithAvailablePasswordOrBiometricPassword()
        } else {
           confirmPasswordOrBiometricPasswordIsSuccessfully()
        }

        binding.inc.collapse.title = getString(R.string.setting)
        binding.inc.toolbar.setNavigationOnClickListener { finish() }

        roomBackup = RoomBackup(this)
        downloadReceiver = DownloadReceiver()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            registerReceiver(downloadReceiver,IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                RECEIVER_NOT_EXPORTED
            )

        } else {
            registerReceiver(downloadReceiver,IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }


    }

    private fun showSettingsFragment(){
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_settings,SettingsFragment())
        transaction.commit()
    }

    override fun confirmPasswordOrBiometricPasswordIsSuccessfully() {
        showSettingsFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }

}
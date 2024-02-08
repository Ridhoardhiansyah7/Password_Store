package com.onedive.passwordstore.presentation.activity

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import com.onedive.passwordstore.R
import com.onedive.passwordstore.databinding.ActivitySettingsBinding
import com.onedive.passwordstore.presentation.fragment.SettingsFragment
import com.onedive.passwordstore.presentation.receiver.DownloadReceiver
import de.raphaelebner.roomdatabasebackup.core.RoomBackup

class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {

    lateinit var roomBackup: RoomBackup
    private lateinit var downloadReceiver : DownloadReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySettingsBinding.inflate(layoutInflater))


        binding.inc.collapse.title = getString(R.string.setting)
        binding.inc.toolbar.setNavigationOnClickListener { finish() }
        roomBackup = RoomBackup(this)

        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.container_settings, SettingsFragment())
        transition.commit()

        downloadReceiver = DownloadReceiver()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            registerReceiver(downloadReceiver,IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                RECEIVER_NOT_EXPORTED
            )

        } else {
            registerReceiver(downloadReceiver,IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }

}
package com.onedive.passwordstore.presentation.activity

import android.os.Bundle
import com.onedive.passwordstore.R
import com.onedive.passwordstore.databinding.ActivitySettingsBinding
import com.onedive.passwordstore.presentation.fragment.SettingsFragment
import de.raphaelebner.roomdatabasebackup.core.RoomBackup

class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {

    lateinit var roomBackup: RoomBackup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySettingsBinding.inflate(layoutInflater))


        binding.inc.collapse.title = getString(R.string.setting)
        binding.inc.toolbar.setNavigationOnClickListener { finish() }
        roomBackup = RoomBackup(this)

        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.container_settings, SettingsFragment())
        transition.commit()

    }

}
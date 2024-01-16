package com.onedive.passwordstore.presentation.fragment

import android.app.KeyguardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.onedive.passwordstore.R
import com.onedive.passwordstore.data.repositoryImpl.BackupRestoreRoomDatabaseImpl
import com.onedive.passwordstore.presentation.activity.SettingsActivity
import com.onedive.passwordstore.presentation.viewmodel.BackupRestoreDataViewModel
import com.onedive.passwordstore.presentation.viewmodel.factory.BackupRestoreDataViewModelFactory
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.sharedPreference
import com.onedive.passwordstore.utils.showDialog

/**
 * This class is the class that manages the setting preferences for this project
 */
class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var fragmentActivity: SettingsActivity
    private lateinit var lockAppPreference: SwitchPreference

    private val viewModel: BackupRestoreDataViewModel by viewModels {
        BackupRestoreDataViewModelFactory(
            BackupRestoreRoomDatabaseImpl(
                fragmentActivity.roomBackup,
                requireContext()
            )
        )
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)

        fragmentActivity = (activity as SettingsActivity)
        lockAppPreference = findPreference("lock")!!
        val backupPreference: Preference = findPreference("backup")!!
        val restorePreference: Preference = findPreference("restore")!!


        backupPreference.setOnPreferenceClickListener {
            showBackupDataDialog()
            true
        }

        restorePreference.setOnPreferenceClickListener {
            showRestoreDataDialog()
            true
        }
        checkDeviceSecurity()
    }

    private fun checkDeviceSecurity() {
        val keyguardManager =
            requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (keyguardManager.isKeyguardSecure) {

            lockAppPreference.isEnabled = true
            lockAppPreference.summary = getString(R.string.lock_preference_summary)

            lockAppPreference.setOnPreferenceChangeListener { _, newValue ->
                sharedPreference(requireContext()).edit()
                    .putBoolean(Const.LOCK_APP_KEY_PREFERENCE, newValue as Boolean).apply()
                true
            }

        }

    }

    private fun showBackupDataDialog() {
        showDialog(
            context = requireContext(),
            title = getString(R.string.backup_data_message_title_dialog),
            message = getString(R.string.backup_data_message_summary_dialog),
            positiveBtnText = getString(R.string.backup_data_message_title_dialog),
            negativeBtnText = getString(R.string.confirm_close),
            positiveBtnClick = { viewModel.backupData() },
            neutralBtnClick = null
        )
    }


    private fun showRestoreDataDialog() {
        showDialog(
            context = requireContext(),
            title = getString(R.string.restore_data_message_title_dialog),
            message = getString(R.string.restore_data_message_summary_dialog),
            positiveBtnText = getString(R.string.restore_data_message_title_dialog),
            negativeBtnText = getString(R.string.confirm_close),
            positiveBtnClick = { viewModel.restoreData() },
            neutralBtnClick = null
        )
    }

    override fun onResume() {
        super.onResume()

        viewModel.backupRestoreResult.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

    }
}
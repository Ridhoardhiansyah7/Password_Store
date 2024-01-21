package com.onedive.passwordstore.presentation.fragment

import android.Manifest
import android.app.DownloadManager
import android.app.KeyguardManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.getSystemService
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.onedive.passwordstore.BuildConfig
import com.onedive.passwordstore.R
import com.onedive.passwordstore.data.repositoryImpl.BackupRestoreRoomDatabaseImpl
import com.onedive.passwordstore.data.repositoryImpl.UpdateRepositoryImpl
import com.onedive.passwordstore.presentation.activity.SettingsActivity
import com.onedive.passwordstore.presentation.viewmodel.BackupRestoreDataViewModel
import com.onedive.passwordstore.presentation.viewmodel.UpdateViewModel
import com.onedive.passwordstore.presentation.viewmodel.factory.BackupRestoreDataViewModelFactory
import com.onedive.passwordstore.presentation.viewmodel.factory.UpdateViewModelFactory
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.sharedPreference
import com.onedive.passwordstore.utils.showDialog
import com.onedive.passwordstore.utils.showToast

/**
 * This class is the class that manages the setting preferences for this project
 */
class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var fragmentActivity: SettingsActivity
    private lateinit var lockAppPreference: SwitchPreference
    private lateinit var updateAppPreference: Preference

    private val backUpViewModel: BackupRestoreDataViewModel by viewModels {
        BackupRestoreDataViewModelFactory(
            BackupRestoreRoomDatabaseImpl(
                fragmentActivity.roomBackup,
                requireContext()
            )
        )
    }

    private val updateViewModel: UpdateViewModel by viewModels {
        UpdateViewModelFactory(UpdateRepositoryImpl())
    }



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)

        fragmentActivity = (activity as SettingsActivity)
        lockAppPreference = findPreference("lock")!!
        updateAppPreference = findPreference("checkUpdate")!!

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

        updateAppPreference.setOnPreferenceClickListener {
            updateViewModel.checkAvailableUpdate()
            true
        }

        checkDeviceSecurity()
        observeUpdateLiveData()
    }

    private fun checkDeviceSecurity() {
        val keyguardManager =
            requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (keyguardManager.isKeyguardSecure) {

            lockAppPreference.isEnabled = true
            lockAppPreference.summary = getString(R.string.lock_preference_summary)

            lockAppPreference.setOnPreferenceChangeListener { _, newValue ->
                sharedPreference(requireContext())
                    .edit()
                    .putBoolean(Const.LOCK_APP_KEY_PREFERENCE, newValue as Boolean)
                    .apply()
                true
            }

        }

    }

    private fun observeUpdateLiveData() {

        updateViewModel.updateLiveData.observe(this) { update ->

            if (BuildConfig.VERSION_NAME != update.updateVersion) {
                showDialog(
                    context = requireContext(),
                    title = update.updateTitle,
                    message = update.updateMessage,
                    positiveBtnText = "Update",
                    negativeBtnText = "Close",
                    positiveBtnClick = { showPermissionDialog(update.updateApkUrl) },
                    neutralBtnClick = null
                )
            } else {
                showToast(
                    context = requireContext(),
                    message = getString(R.string.no_available_update),
                    duration = Toast.LENGTH_LONG
                )
            }

        }
    }

    private fun showPermissionDialog(url : String){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ){
                if (it) {
                    downloadAndInstallUpdate(url)
                } else {
                    showToast(
                        context = requireContext(),
                        message = "Permission is required to download files",
                        duration = Toast.LENGTH_LONG
                    )
                }
            }
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

        } else {
            downloadAndInstallUpdate(url)
        }

    }

    private fun downloadAndInstallUpdate(url : String) {

        val request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationInExternalFilesDir(requireContext(),Environment.DIRECTORY_DOWNLOADS,"${getString(R.string.app_name)}.apk")
            .setTitle(getString(R.string.app_name))
            .setDescription("Downloading")
            .setAllowedOverRoaming(true)
            .setAllowedOverMetered(true)

        val downloadManager = fragmentActivity.getSystemService<DownloadManager>()
        downloadManager!!.enqueue(request)

    }


    private fun showBackupDataDialog() {
        showDialog(
            context = requireContext(),
            title = getString(R.string.backup_data_message_title_dialog),
            message = getString(R.string.backup_data_message_summary_dialog),
            positiveBtnText = getString(R.string.backup_data_message_title_dialog),
            negativeBtnText = getString(R.string.confirm_close),
            positiveBtnClick = { backUpViewModel.backupData() },
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
            positiveBtnClick = { backUpViewModel.restoreData() },
            neutralBtnClick = null
        )
    }

    override fun onResume() {
        super.onResume()

        backUpViewModel.backupRestoreResult.observe(viewLifecycleOwner) {
            showToast(requireContext(), it, Toast.LENGTH_LONG)
        }

    }
}
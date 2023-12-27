package com.onedive.passwordstore.uiLayer.fragment

import android.app.KeyguardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.onedive.passwordstore.R
import com.onedive.passwordstore.domainLayer.repository.impl.BackupRestoreRoomDatabaseImpl
import com.onedive.passwordstore.uiLayer.activity.SettingsActivity
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.getDefaultSharedPreference
import com.onedive.passwordstore.utils.getEncryptedSharedPreferences
import com.onedive.passwordstore.utils.showDialog
import com.onedive.passwordstore.viewmodel.BackupRestoreDataViewModel
import com.onedive.passwordstore.viewmodel.factory.BackupRestoreDataViewModelFactory

/**
 * This class is the class that manages the setting preferences for this project
 */
class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var fragmentActivity: SettingsActivity
    private lateinit var keyguardManager:KeyguardManager

    private lateinit var lockAppPreference: SwitchPreference
    private lateinit var encryptDatabase: SwitchPreference
    private lateinit var backupPreference: Preference

    private lateinit var restorePreference: Preference
    private val viewModel:BackupRestoreDataViewModel by viewModels {
        BackupRestoreDataViewModelFactory(BackupRestoreRoomDatabaseImpl(fragmentActivity.roomBackup,requireContext()))
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey)
        fragmentActivity = (activity as SettingsActivity)


        lockAppPreference = findPreference("lock")!!
        encryptDatabase = findPreference("encrypt")!!
        backupPreference = findPreference("backup")!!
        restorePreference = findPreference("restore")!!


        keyguardManager = requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (keyguardManager.isKeyguardSecure) {
            lockAppPreference.isEnabled = true
            lockAppPreference.summary = getString(R.string.enter_device_password_preference)

            lockAppPreference.setOnPreferenceChangeListener { _, newValue ->
                getDefaultSharedPreference(requireContext()).edit().putBoolean(Const.LOCK_APP_KEY_PREFERENCE, newValue as Boolean).apply()
                true
            }

        }

        backupPreference.setOnPreferenceClickListener {
            showBackupDataDialog()
            true
        }

        restorePreference.setOnPreferenceClickListener {
            showRestoreDataDialog()
            true
        }

        if (getEncryptedSharedPreferences(requireContext()).getString(Const.ENCRYPT_KEY_PREFERENCE,null) == null){

             encryptDatabase.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean){ showEncryptDataDialog() }
                true
            }

        }else{

            encryptDatabase.isEnabled = false
            backupPreference.isEnabled = true

            backupPreference.summary = getString(R.string.backup_preference)
            restorePreference.summary = getString(R.string.restore_preference)

            restorePreference.isEnabled = true
        }


    }


    private fun showEncryptDataDialog(){

        val view = LayoutInflater.from(fragmentActivity).inflate(R.layout.dialog_encrypt,null)
        val editText = view.findViewById<TextInputEditText>(R.id.encrypt_edt)

        val alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.title_set_encrypt_key))
            .setMessage(getString(R.string.message_set_encrypt_key))
            .setCancelable(false)
            .setView(view)
            .setPositiveButton(getString(R.string.confirm_ok)){ dialogInterface, _ ->

                if (editText.text.toString().isBlank()){
                    Toast.makeText(requireContext(),getString(R.string.key_cannot_null),Toast.LENGTH_SHORT).show()
                    encryptDatabase.isChecked = false

                } else {
                    getEncryptedSharedPreferences(requireContext()).edit().putString(Const.ENCRYPT_KEY_PREFERENCE,editText.text.toString()).apply()
                    encryptDatabase.isEnabled = false
                    dialogInterface.dismiss()
                    requireActivity().finish()
                }

            }
            .setNeutralButton(getString(R.string.confirm_close)){ dialogInterface, _ ->
                encryptDatabase.isChecked = false
                dialogInterface.dismiss()
            }

        alertDialog.show()
    }

    private fun showBackupDataDialog() {
        showDialog(
            context = requireContext(),
            title = getString(R.string.backup_data_title),
            message = getString(R.string.backup_data_message),
            positiveBtnText = "Backup",
            negativeBtnText = "Close",
            positiveBtnClick = { viewModel.backupData() },
            neutralBtnClick = null
        )
    }


    private fun showRestoreDataDialog() {
        showDialog(
            context = requireContext(),
            title = getString(R.string.restore_data_title),
            message = getString(R.string.restore_data_message),
            positiveBtnText = "Restore",
            negativeBtnText = "Close",
            positiveBtnClick = { viewModel.restoreData() },
            neutralBtnClick = null
        )
    }

    override fun onResume() {
        super.onResume()

        viewModel.backupRestoreResult.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        }

    }
}
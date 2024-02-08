package com.onedive.passwordstore.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.onedive.passwordstore.R
import com.onedive.passwordstore.databinding.ActivityInitEncryptDatabaseBinding
import com.onedive.passwordstore.utils.encryptedSharedPreferences
import com.onedive.passwordstore.utils.sharedPreference
import com.onedive.passwordstore.utils.showToast

class ActivityInitEncryptDatabase : BaseActivity<ActivityInitEncryptDatabaseBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityInitEncryptDatabaseBinding.inflate(layoutInflater))

        if (sharedPreference(this).getBoolean(activityHasBeenOpenedOnce,false)){
            closeThisActivityWhenThisActivityIsOpened()
        }
        
        binding.toolbar.setNavigationOnClickListener { closeThisActivityAndMarkThisActivityIsUnOpened() }
        binding.btnClose.setOnClickListener { closeThisActivityAndMarkThisActivityIsUnOpened() }
        binding.btnOk.setOnClickListener { checkValuesOfEditTextPasswordAndEditTextReconfirm() }

    }

   private fun checkValuesOfEditTextPasswordAndEditTextReconfirm(){

       val password = binding.edtPass1.text.toString().trim()
       val reConfirmPassword = binding.edtPass2.text.toString().trim()

       if (password.isNotEmpty() && reConfirmPassword.isNotEmpty()){

           sharedPreference(this)
               .edit()
               .putBoolean(activityHasBeenOpenedOnce,true)
               .apply()

           encryptedSharedPreferences(this)
               .edit()
               .putString(passwordValuesPreference,reConfirmPassword)
               .apply()

           closeThisActivityWhenThisActivityIsOpened()

       } else if (password != reConfirmPassword) {
           showToast(
               context = this,
               message = getString(R.string.input_pass_not_equals),
               duration = Toast.LENGTH_LONG
           )
       } else {
           showToast(
               context = this,
               message = getString(R.string.input_cannot_empty),
               duration = Toast.LENGTH_LONG
           )
       }
       
   }
    
    private fun closeThisActivityAndMarkThisActivityIsUnOpened(){
        sharedPreference(this).edit().putBoolean(activityHasBeenOpenedOnce,false).apply()
        finish()
    }

    private fun closeThisActivityWhenThisActivityIsOpened(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    companion object {
        private const val activityHasBeenOpenedOnce = "isOpened"
        const val passwordValuesPreference = "passEncryptPreference"
    }
}
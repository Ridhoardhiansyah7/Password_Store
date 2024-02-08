package com.onedive.passwordstore.presentation.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.onedive.passwordstore.R
import com.onedive.passwordstore.databinding.ActivityCrashBinding
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.showDialog
import com.onedive.passwordstore.utils.showToast
import java.io.IOException

/**
 * This class is used to display errors that occur in this application,
 *
 *
 * for more documentation please see https://github.com/Ereza/CustomActivityOnCrash
 * @see com.onedive.passwordstore.presentation.MyApplication
 * @see CustomActivityOnCrash
 * @see CaocConfig
 *
 */
class CrashActivity : BaseActivity<ActivityCrashBinding>(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityCrashBinding.inflate(layoutInflater))

        binding.btnDeleteAllDataInApp.setOnClickListener {
            showDialog(
                context = this,
                title = "Delete",
                message = " Are you sure? To delete all data in this app, press ok if you are having" +
                        " problems selecting the database file that is returned to the app, or any" +
                        " other issues that are causing this app to not work.",
                positiveBtnText = getString(R.string.confirm_ok),
                negativeBtnText = getString(R.string.confirm_close),
                positiveBtnClick = { clearAppData() } ,
                neutralBtnClick = null
            )
        }

        binding.btnShowLog.setOnClickListener {
            val message = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this,intent)
            showDialog(
                context = this,
                title = "Error Log",
                message = message,
                positiveBtnText = "ReportToDeveloper",
                negativeBtnText = "Close",
                positiveBtnClick = {shareErrorLogToDeveloper(message)},
                neutralBtnClick = null
            )

        }

        binding.btnRestart.setOnClickListener {
            val config= CustomActivityOnCrash.getConfigFromIntent(intent) !!
            CustomActivityOnCrash.restartApplication(this,config)
        }


    }

    private fun clearAppData(){

        try {
            val packageInfo = packageManager.getPackageInfo(packageName,PackageManager.GET_META_DATA)

            val clearAll = Runtime
                .getRuntime()
                .exec("pm clear ${packageInfo.packageName}")

            clearAll.waitFor()
        }catch (e:IOException){
            showToast(
                context = this,
                message = "Failed to delete all application data because : ${e.localizedMessage}",
                duration = Toast.LENGTH_LONG
            )
        }
    }

    //share error log to developer whatsapp
    private fun shareErrorLogToDeveloper(stackTraceError:String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(
            "https://wa.me/${Const.DEVELOPER_CONTACT_NUMBER}?text=$stackTraceError"
        )
        try {
            startActivity(intent)
        }catch (e:Exception){
            showToast(
                context = this,
                message = "Unable to open this link, it may be because your phone does not have" +
                        " the right application to open this link/document.",
                duration = Toast.LENGTH_SHORT
            )
        }

    }
}
package com.onedive.passwordstore.presentation.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.onedive.passwordstore.databinding.ActivityCrashBinding
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.showDialog

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
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }

    private fun shareErrorLogToDeveloper(stackTraceError:String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://wa.me/${Const.DEVELOPER_CONTACT_NUMBER}?text=$stackTraceError")
        try {
            startActivity(intent)
        }catch (e:Exception){
            Toast.makeText(this,"Sorry, it looks like your device doesn't have the right application to open this document/link",Toast.LENGTH_SHORT).show()
        }

    }
}
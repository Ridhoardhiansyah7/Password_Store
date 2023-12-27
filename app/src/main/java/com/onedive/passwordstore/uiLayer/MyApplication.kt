package com.onedive.passwordstore.uiLayer

import android.app.Application
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.onedive.passwordstore.uiLayer.activity.CrashActivity

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CaocConfig.Builder.create()
            .errorActivity(CrashActivity::class.java)
            .showErrorDetails(true)
            .trackActivities(true)
            .apply()
    }
}
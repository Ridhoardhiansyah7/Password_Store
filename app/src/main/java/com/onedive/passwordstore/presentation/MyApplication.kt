package com.onedive.passwordstore.presentation

import android.app.Application
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.onedive.passwordstore.presentation.activity.CrashActivity

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        caocConfigBuilder().apply()
    }

    private fun caocConfigBuilder() = CaocConfig.Builder.create().apply {
        errorActivity(CrashActivity::class.java)
        showRestartButton(true)
        showErrorDetails(true)
        trackActivities(true)
    }

}
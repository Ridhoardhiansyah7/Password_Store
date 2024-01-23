package com.onedive.passwordstore.presentation.activity

import android.os.Bundle
import com.onedive.passwordstore.databinding.ActivityAboutAppBinding

class AboutAppActivity : BaseActivity<ActivityAboutAppBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityAboutAppBinding.inflate(layoutInflater))

        binding.inc.toolbar.title = "About App"
        binding.inc.toolbar.setNavigationOnClickListener { finish() }

    }

}
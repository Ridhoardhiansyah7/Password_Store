package com.onedive.passwordstore.uiLayer.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.onedive.passwordstore.R
import com.onedive.passwordstore.databinding.ActivityDetailBinding
import com.onedive.passwordstore.domainLayer.dataSource.room.entity.PasswordRoomEntity
import com.onedive.passwordstore.domainLayer.repository.impl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.utils.Const.EXTRA_DETAIL_KEY
import com.onedive.passwordstore.utils.Const.EXTRA_EDIT
import com.onedive.passwordstore.utils.toAnotherActivity
import com.onedive.passwordstore.viewmodel.PasswordViewModel
import com.onedive.passwordstore.viewmodel.factory.PasswordViewModelFactory

class DetailPasswordActivity : BaseSecurityActivity<ActivityDetailBinding>() {

    private var id:Long = 0

    private val viewModel:PasswordViewModel<PasswordRoomEntity> by viewModels {
        PasswordViewModelFactory(RoomDatabaseRepositoryImpl(roomDatabaseDao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityDetailBinding.inflate(layoutInflater))

        setSupportActionBar(binding.inc.toolbar)
        binding.inc.collapse.title = "Detail Password"
        binding.inc.toolbar.setNavigationOnClickListener { finish() }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit){
            toAnotherActivity(
                key = EXTRA_EDIT,
                value = id.toString(),
                from = this,
                to = EditPasswordActivity::class.java
            )
        }else{
            viewModel.deleteById(id)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun deviceSecurityIsAvailable() {
        if (intent.hasExtra(EXTRA_DETAIL_KEY)){
            id = intent.getStringExtra(EXTRA_DETAIL_KEY)?.toLong() ?: -1
            showData()
        }
    }

    override fun deviceSecurityIsNotAvailable() {
        if (intent.hasExtra(EXTRA_DETAIL_KEY)){
            id = intent.getStringExtra(EXTRA_DETAIL_KEY)?.toLong() ?: -1
            showData()
        }
    }



    private fun showData(){
        viewModel.getDetailedItemById(id).observe(this){
            binding.edtTitle.setText(it.title)
            binding.edtUsername.setText(it.username)
            binding.edtPassword.setText(it.password)
            binding.edtDesc.setText(it.desc)
            binding.edtTags.setText(it.tags)
            binding.edtDate.setText(it.date)
        }
    }
}

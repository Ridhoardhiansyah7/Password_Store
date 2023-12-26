package com.onedive.passwordstore.uiLayer.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.onedive.passwordstore.R
import com.onedive.passwordstore.domainLayer.dataSource.room.entity.PasswordRoomEntity
import com.onedive.passwordstore.domainLayer.repository.impl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.utils.Const.EXTRA_EDIT
import com.onedive.passwordstore.viewmodel.PasswordViewModel
import com.onedive.passwordstore.viewmodel.factory.PasswordViewModelFactory

class EditPasswordActivity  : AddPasswordActivity() {

    private var id:Long = 0

    private val viewModel: PasswordViewModel<PasswordRoomEntity> by viewModels {
        PasswordViewModelFactory(RoomDatabaseRepositoryImpl(roomDatabaseDao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.hasExtra(EXTRA_EDIT)){
            id = intent.getStringExtra(EXTRA_EDIT)?.toLong() ?: -1
            setDefaultInputValue()
        }

        binding.inc.collapse.title = "Edit Password"
        binding.inc.toolbar.setNavigationOnClickListener { finish() }

    }


    private fun setDefaultInputValue(){
        viewModel.getDetailedItemById(id).observe(this){

            binding.edtTitle.setText(it.title)
            binding.edtUsername.setText(it.username)
            binding.edtPassword.setText(it.password)
            binding.edtRePassword.setText(it.password)
            binding.edtDesc.setText(it.desc)
            binding.edtTags.setText(it.tags)

        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add){
            saveData(id)
        }
        return true
    }


}
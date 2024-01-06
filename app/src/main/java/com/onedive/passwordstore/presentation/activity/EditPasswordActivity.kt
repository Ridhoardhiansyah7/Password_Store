package com.onedive.passwordstore.presentation.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.onedive.passwordstore.R
import com.onedive.passwordstore.data.repositoryImpl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.domain.model.DatabaseModelDTO
import com.onedive.passwordstore.presentation.viewmodel.PasswordViewModel
import com.onedive.passwordstore.presentation.viewmodel.factory.PasswordViewModelFactory
import com.onedive.passwordstore.utils.Const.EXTRA_EDIT

class EditPasswordActivity  : AddPasswordActivity() {

    private var id:Long = 0

    private val viewModel: PasswordViewModel<DatabaseModelDTO> by viewModels {
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
        viewModel.getDetailedItemById(id)

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
package com.onedive.passwordstore.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.onedive.passwordstore.R
import com.onedive.passwordstore.data.repositoryImpl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.presentation.viewmodel.PasswordViewModel
import com.onedive.passwordstore.presentation.viewmodel.factory.PasswordViewModelFactory
import com.onedive.passwordstore.utils.Const.EXTRA_EDIT

class EditPasswordActivity  : AddPasswordActivity() {

    private var id:Long = 0

    private val viewModel: PasswordViewModel by viewModels {
        PasswordViewModelFactory(RoomDatabaseRepositoryImpl(roomDatabaseDao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        Check if the ID data sent from the previous activity exists,
        if there is then set the Variable ID to be the ID data of the database item that was sent earlier
         */
        if (intent.hasExtra(EXTRA_EDIT)){
            id = intent.getStringExtra(EXTRA_EDIT)?.toLong() ?: -1
            setDefaultInputValue()
        }

        binding.inc.collapse.title = "Edit Password"
        binding.inc.toolbar.setNavigationOnClickListener { finish() }

    }


    @SuppressLint("SetTextI18n")
    // Display the details of the data to be edited
    private fun setDefaultInputValue(){

        viewModel.getDetailedItemById(id).observe(this){
            binding.edtTitle.setText(it.title)
            binding.edtUsername.setText(it.username)
            binding.edtPassword.setText(it.password)
            binding.edtRePassword.setText(it.password)
            binding.edtDesc.setText(it.desc)
            binding.edtTags.setText(it.tags)
            binding.edtColor.setText("#${it.roundedColor}")
            binding.imgPreview.setBackgroundColor(it.roundedColor)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add){
            saveData(id) //save data
        }
        return true
    }


}
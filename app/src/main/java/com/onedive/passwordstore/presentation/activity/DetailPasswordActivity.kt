package com.onedive.passwordstore.presentation.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.onedive.passwordstore.R
import com.onedive.passwordstore.data.repositoryImpl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.databinding.ActivityDetailBinding
import com.onedive.passwordstore.domain.model.DatabaseModelDTO
import com.onedive.passwordstore.presentation.viewmodel.PasswordViewModel
import com.onedive.passwordstore.presentation.viewmodel.factory.PasswordViewModelFactory
import com.onedive.passwordstore.utils.Const.EXTRA_DETAIL_KEY
import com.onedive.passwordstore.utils.Const.EXTRA_EDIT
import com.onedive.passwordstore.utils.toAnotherActivity

class DetailPasswordActivity : BaseSecurityActivity<ActivityDetailBinding>() {

    private var id:Long = 0

    private val viewModel: PasswordViewModel<DatabaseModelDTO> by viewModels {
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
        menuInflater.inflate(R.menu.edit_delete_menu,menu)
        menu.findItem(R.id.action_delete).setVisible(false)
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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun deviceSecurityIsAvailable() {
        showData()
    }

    override fun deviceSecurityIsNotAvailable() {
        showData()
    }



    private fun showData(){

        if (intent.hasExtra(EXTRA_DETAIL_KEY)){
            id = intent.getStringExtra(EXTRA_DETAIL_KEY)?.toLong() ?: -1
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
}

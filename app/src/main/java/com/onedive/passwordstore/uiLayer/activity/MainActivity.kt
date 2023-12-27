package com.onedive.passwordstore.uiLayer.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.view.ActionMode
import com.onedive.passwordstore.R
import com.onedive.passwordstore.databinding.ActivityMainBinding
import com.onedive.passwordstore.domainLayer.dataSource.room.entity.PasswordRoomEntity
import com.onedive.passwordstore.domainLayer.repository.impl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.uiLayer.adapter.PasswordDataAdapter
import com.onedive.passwordstore.uiLayer.adapter.PasswordDataTypeAdapter
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.Const.EXTRA_DETAIL_KEY
import com.onedive.passwordstore.utils.Const.EXTRA_LIST_BY_TAG
import com.onedive.passwordstore.utils.toAnotherActivity
import com.onedive.passwordstore.viewmodel.PasswordViewModel
import com.onedive.passwordstore.viewmodel.factory.PasswordViewModelFactory

class MainActivity : BaseSecurityActivity<ActivityMainBinding>() {

    private var actionMode:ActionMode? = null
    private var id:Long = 0

    private val viewModel: PasswordViewModel<PasswordRoomEntity> by viewModels {
        PasswordViewModelFactory(RoomDatabaseRepositoryImpl(roomDatabaseDao))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater))
        setSupportActionBar(binding.inc.toolbar)

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddPasswordActivity::class.java))
        }
        binding.inc.toolbar.navigationIcon = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings){
            startActivity(Intent(this,SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun deviceSecurityIsAvailable() {
        setAdapterPasswordData()
        setAdapterDistrictTypeData()
    }

    override fun deviceSecurityIsNotAvailable() {
        setAdapterPasswordData()
        setAdapterDistrictTypeData()
    }

    private fun setAdapterPasswordData() {
        viewModel.getAll.observe(this) { list ->

            PasswordDataAdapter(
                context = this,
                list =  list ,
                onClick = {
                    toAnotherActivity(
                        key = EXTRA_DETAIL_KEY,
                        value = list[it].id.toString(),
                        from = this,
                        to = DetailPasswordActivity::class.java
                    )
                },
                onLongClick = {
                   if (actionMode == null){
                       id = list[it].id!!
                       actionMode = startSupportActionMode(actionCallback)
                   }
                }

            ).also {
                binding.rvPassword.adapter = it

                if (it.itemCount == 0){
                    binding.lottieParent.parent.visibility = View.VISIBLE
                }else{
                    binding.lottieParent.parent.visibility = View.GONE
                }

            }
        }
    }

    private fun setAdapterDistrictTypeData() {
        viewModel.getDistrictTag.observe(this) { list ->

            PasswordDataTypeAdapter(
                tagName = list,
                onClick = {
                    toAnotherActivity(
                        key = EXTRA_LIST_BY_TAG,
                        value = list[it],
                        from = this,
                        to = ListByTagActivity::class.java
                    )
                },
                context = this

            ).also { adapter -> binding.rvTags.adapter = adapter }
        }
    }

    private val actionCallback:ActionMode.Callback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.edit_delete_menu, menu)
            mode.title = getString(R.string.select_option_here)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when(item.itemId){

                R.id.action_edit -> {
                    toAnotherActivity(Const.EXTRA_EDIT,id.toString(),this@MainActivity,EditPasswordActivity::class.java)
                    mode.finish()
                    true
                }

                R.id.action_delete ->{
                    viewModel.deleteById(id)
                    mode.finish()
                    true
                }

                else ->  false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
        }
    }
}
package com.onedive.passwordstore.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import com.onedive.passwordstore.R
import com.onedive.passwordstore.data.dataSource.local.room.entity.PasswordRoomDatabaseEntity
import com.onedive.passwordstore.data.repositoryImpl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.databinding.ActivityMainBinding
import com.onedive.passwordstore.presentation.adapter.PasswordDataAdapter
import com.onedive.passwordstore.presentation.adapter.PasswordDataTypeAdapter
import com.onedive.passwordstore.presentation.viewmodel.PasswordViewModel
import com.onedive.passwordstore.presentation.viewmodel.factory.PasswordViewModelFactory
import com.onedive.passwordstore.utils.Const.EXTRA_DETAIL_KEY
import com.onedive.passwordstore.utils.Const.EXTRA_EDIT
import com.onedive.passwordstore.utils.Const.EXTRA_LIST_BY_TAG
import com.onedive.passwordstore.utils.toAnotherActivity

class MainActivity : BaseSecurityActivity<ActivityMainBinding>() {

    private var actionMode : ActionMode? = null
    private lateinit var passwordDataAdapter: PasswordDataAdapter
    private var id: Long = 0 // database item id
    private var position: Int = 0 // recyclerview item position

    private val viewModel: PasswordViewModel<PasswordRoomDatabaseEntity> by viewModels {
        PasswordViewModelFactory(RoomDatabaseRepositoryImpl(roomDatabaseDao))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater))
        setSupportActionBar(binding.toolbar)

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddPasswordActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return true
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
        viewModel.getAll().observe(this) { list ->

            passwordDataAdapter = PasswordDataAdapter(context = this, list = list, onClick = {
                toAnotherActivity(
                    key = EXTRA_DETAIL_KEY,
                    value = list[it].id.toString(),
                    from = this,
                    to = DetailPasswordActivity::class.java
                )},
                onLongClick = { // can only select single data

                    if (actionMode == null){
                        actionMode = binding.toolbar.startActionMode(actionCallback)

                        id = list[it].id!!
                        position = it

                        binding.rvPassword.layoutManager!!.findViewByPosition(position)!!.apply {
                            background = AppCompatResources.getDrawable(this@MainActivity,android.R.color.darker_gray)
                        }

                    }
                }
            )
            binding.rvPassword.adapter = passwordDataAdapter
            if (passwordDataAdapter.itemCount == 0) {
                binding.lottieParent.parent.visibility = View.VISIBLE
            }  else {
                binding.lottieParent.parent.visibility = View.GONE
            }

        }
    }

    private fun setAdapterDistrictTypeData() {
        viewModel.getDistinctTags().observe(this) { list ->

            PasswordDataTypeAdapter(context = this, tagName = list, onClick = {
                toAnotherActivity(
                    key = EXTRA_LIST_BY_TAG,
                    value = list[it],
                    from = this,
                    to = ListByTagActivity::class.java
                )},

            ).also {  binding.rvTags.adapter = it }
        }
    }

    private val actionCallback: ActionMode.Callback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.edit_delete_menu, menu)
            mode.title = getString(R.string.select_option_here)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean = false


        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {

                R.id.action_edit -> {
                    toAnotherActivity(
                        key = EXTRA_EDIT,
                        value = id.toString(),
                        from = this@MainActivity,
                        to = EditPasswordActivity::class.java
                    )
                    mode.finish()
                    true
                }

                R.id.action_delete -> {
                    viewModel.deleteById(id)
                    passwordDataAdapter.notifyItemRemoved(position)
                    mode.finish()
                    true
                }

                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            binding.rvPassword.layoutManager!!.findViewByPosition(position)!!.background = null
            mode.finish()
            actionMode = null
        }

    }

}
package com.onedive.passwordstore.presentation.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import com.onedive.passwordstore.R
import com.onedive.passwordstore.data.repositoryImpl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.databinding.ActivityMainBinding
import com.onedive.passwordstore.domain.model.DatabaseModelDTO
import com.onedive.passwordstore.presentation.adapter.PasswordDataAdapter
import com.onedive.passwordstore.presentation.adapter.PasswordDataTypeAdapter
import com.onedive.passwordstore.presentation.viewmodel.PasswordViewModel
import com.onedive.passwordstore.presentation.viewmodel.factory.PasswordViewModelFactory
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.Const.EXTRA_DETAIL_KEY
import com.onedive.passwordstore.utils.Const.EXTRA_EDIT
import com.onedive.passwordstore.utils.Const.EXTRA_LIST_BY_TAG
import com.onedive.passwordstore.utils.sharedPreference
import com.onedive.passwordstore.utils.showDialog
import com.onedive.passwordstore.utils.toAnotherActivity

class MainActivity : BaseSecurityActivity<ActivityMainBinding>() {

    private lateinit var passwordDataAdapter: PasswordDataAdapter
    private lateinit var popupMenu: PopupMenu

    private val viewModel: PasswordViewModel by viewModels {
        PasswordViewModelFactory(RoomDatabaseRepositoryImpl(roomDatabaseDao))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater))
        setSupportActionBar(binding.toolbar)

        if (sharedPreference(this).getBoolean(Const.LOCK_APP_KEY_PREFERENCE,false)){
            showConfirmDialogWithAvailablePasswordOrBiometricPassword()
        } else {
            noAvailablePasswordOrBiometricPasswordInThisDevice()
        }

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

    override fun confirmPasswordOrBiometricPasswordIsSuccessfully() {
        setAdapterPasswordData()
        setAdapterDistrictTypeData()
    }

    override fun noAvailablePasswordOrBiometricPasswordInThisDevice() {
        setAdapterPasswordData()
        setAdapterDistrictTypeData()
    }


    private fun setAdapterPasswordData() {

        viewModel.getAll().observe(this) { list ->

            passwordDataAdapter = PasswordDataAdapter(
                context = this,
                list = list,
                onClick = { toDetailPassword(list[it].id!!) },
                onLongClick = { position, view -> initPopupMenu(view,list,position)}
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
            PasswordDataTypeAdapter(
                context = this,
                tagName = list,
                onClick = { toByTagActivity(list[it]) }
            ).also {
                binding.rvTags.adapter = it
            }
        }

    }

    @SuppressLint("RestrictedApi")
    private fun initPopupMenu(parentView:View,dataList:List<DatabaseModelDTO>,position : Int) {

        popupMenu = PopupMenu(this,parentView,GravityCompat.END)
        popupMenu.menuInflater.inflate(R.menu.edit_delete_menu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {menuItem ->

            when(menuItem.itemId){
                R.id.action_edit -> { toEditActivity(dataList[position].id!!) }
                R.id.action_delete -> { deleteDataPassword(dataList[position].id!!) }
            }

            true
        }

        if (popupMenu.menu is MenuBuilder){
            val menuBuilder = popupMenu.menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)

            for (item in menuBuilder.visibleItems){

                val iconMarginPx = TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP,10.toFloat(),resources.displayMetrics)
                    .toInt()

                if (item.icon != null){

                    item.icon = object : InsetDrawable(item.icon,iconMarginPx,0,iconMarginPx,0){

                        override fun getIntrinsicWidth(): Int {
                            return intrinsicHeight + iconMarginPx + iconMarginPx
                        }

                    }

                }
            }
        }
        popupMenu.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteDataPassword(id:Long){

       showDialog(
            context = this,
            title = getString(R.string.delete_data_message_title_dialog),
            message = getString(R.string.delete_data_message_summary_dialog),
            positiveBtnText = getString(R.string.confirm_ok),
            negativeBtnText = getString(R.string.confirm_close),
            positiveBtnClick = {
                viewModel.deleteById(id)
                passwordDataAdapter.notifyDataSetChanged()
                popupMenu.dismiss()
            },
            neutralBtnClick = null
        )
    }

    private fun toEditActivity(id:Long){
        toAnotherActivity(
            key = EXTRA_EDIT,
            value = id.toString(),
            from = this,
            to = EditPasswordActivity::class.java
        )
        popupMenu.dismiss()
    }

    private fun toDetailPassword(id:Long){
        toAnotherActivity(
            key = EXTRA_DETAIL_KEY,
            value = id.toString(),
            from = this,
            to = DetailPasswordActivity::class.java
        )
    }

    private fun toByTagActivity(tagName:String){
        toAnotherActivity(
            key = EXTRA_LIST_BY_TAG,
            value = tagName,
            from = this,
            to = ListByTagActivity::class.java
        )
    }

}
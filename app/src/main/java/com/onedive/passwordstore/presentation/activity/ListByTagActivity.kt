package com.onedive.passwordstore.presentation.activity

import android.annotation.SuppressLint
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import com.onedive.passwordstore.R
import com.onedive.passwordstore.data.repositoryImpl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.databinding.ActivityListByTagBinding
import com.onedive.passwordstore.domain.model.DatabaseModelDTO
import com.onedive.passwordstore.presentation.adapter.PasswordDataAdapter
import com.onedive.passwordstore.presentation.viewmodel.PasswordViewModel
import com.onedive.passwordstore.presentation.viewmodel.factory.PasswordViewModelFactory
import com.onedive.passwordstore.utils.Const
import com.onedive.passwordstore.utils.Const.EXTRA_DETAIL_KEY
import com.onedive.passwordstore.utils.Const.EXTRA_LIST_BY_TAG
import com.onedive.passwordstore.utils.toAnotherActivity


class ListByTagActivity : BaseActivity<ActivityListByTagBinding>() {

    private lateinit var tagName:String
    private lateinit var passwordDataAdapter:PasswordDataAdapter
    private lateinit var popupMenu: PopupMenu

    private val viewModel: PasswordViewModel by viewModels {
        PasswordViewModelFactory(RoomDatabaseRepositoryImpl(roomDatabaseDao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityListByTagBinding.inflate(layoutInflater))


        binding.inc.toolbar.setNavigationOnClickListener { finish() }

        //Check whether the previously submitted tag name data exists, if there is display data based on the tag
        if (intent.hasExtra(EXTRA_LIST_BY_TAG)){
            tagName = intent.getStringExtra(EXTRA_LIST_BY_TAG)!!
            binding.inc.collapse.title = tagName
            setAdapter()
        }

    }

    private fun setAdapter(){

        viewModel.getAllByTagName(tagName).observe(this){ list ->

            passwordDataAdapter = PasswordDataAdapter(
                context = this,
                list = list ,
                onClick = {
                    toAnotherActivity(
                        key = EXTRA_DETAIL_KEY,
                        value = list[it].id.toString(),
                        from = this,
                        to = DetailPasswordActivity::class.java
                    )
               },
                onLongClick = { position, view-> initPopupMenu(view,list,position)}


            ).also { binding.rvListByType.adapter = it }
        }
    }
    @SuppressLint("RestrictedApi")
    private fun initPopupMenu(parentView : View,dataList : List<DatabaseModelDTO>, position : Int){

        popupMenu = PopupMenu(this,parentView, GravityCompat.END)
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

        com.onedive.passwordstore.utils.showDialog(
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
            key = Const.EXTRA_EDIT,
            value = id.toString(),
            from = this,
            to = EditPasswordActivity::class.java
        )
        popupMenu.dismiss()
    }

}
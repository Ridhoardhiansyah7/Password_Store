package com.onedive.passwordstore.uiLayer.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.onedive.passwordstore.databinding.ActivityListByTagBinding
import com.onedive.passwordstore.domainLayer.dataSource.room.entity.PasswordRoomEntity
import com.onedive.passwordstore.domainLayer.repository.impl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.uiLayer.adapter.PasswordDataAdapter
import com.onedive.passwordstore.utils.Const.EXTRA_DETAIL_KEY
import com.onedive.passwordstore.utils.Const.EXTRA_LIST_BY_TAG
import com.onedive.passwordstore.utils.toAnotherActivity
import com.onedive.passwordstore.viewmodel.PasswordViewModel
import com.onedive.passwordstore.viewmodel.factory.PasswordViewModelFactory


class ListByTagActivity : BaseActivity<ActivityListByTagBinding>() {

    private lateinit var tagName:String

    private val viewModel:PasswordViewModel<PasswordRoomEntity> by viewModels {
        PasswordViewModelFactory(RoomDatabaseRepositoryImpl(roomDatabaseDao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityListByTagBinding.inflate(layoutInflater))


        binding.inc.toolbar.setNavigationOnClickListener { finish() }
        if (intent.hasExtra(EXTRA_LIST_BY_TAG)){
            tagName = intent.getStringExtra(EXTRA_LIST_BY_TAG)!!
            binding.inc.collapse.title = tagName
            setAdapter(viewModel)
        }

    }


    private fun setAdapter(viewModel: PasswordViewModel<PasswordRoomEntity>){
        viewModel.getAllByTagName(tagName).observe(this){ list ->

            PasswordDataAdapter(
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
                onLongClick = {}


            ).also { binding.rvListByType.adapter = it }
        }
    }
}
package com.onedive.passwordstore.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.onedive.passwordstore.data.repositoryImpl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.databinding.ActivityListByTagBinding
import com.onedive.passwordstore.presentation.adapter.PasswordDataAdapter
import com.onedive.passwordstore.presentation.viewmodel.PasswordViewModel
import com.onedive.passwordstore.presentation.viewmodel.factory.PasswordViewModelFactory
import com.onedive.passwordstore.utils.Const.EXTRA_DETAIL_KEY
import com.onedive.passwordstore.utils.Const.EXTRA_LIST_BY_TAG
import com.onedive.passwordstore.utils.toAnotherActivity


class ListByTagActivity : BaseActivity<ActivityListByTagBinding>() {

    private lateinit var tagName:String

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
                onLongClick = {_,_->}


            ).also { binding.rvListByType.adapter = it }
        }
    }
}
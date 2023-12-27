package com.onedive.passwordstore.uiLayer.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.onedive.passwordstore.R
import com.onedive.passwordstore.databinding.ActivityAddBinding
import com.onedive.passwordstore.domainLayer.dataSource.room.entity.PasswordRoomEntity
import com.onedive.passwordstore.domainLayer.repository.impl.RoomDatabaseRepositoryImpl
import com.onedive.passwordstore.utils.getCurrentLocaleTime
import com.onedive.passwordstore.viewmodel.PasswordViewModel
import com.onedive.passwordstore.viewmodel.factory.PasswordViewModelFactory
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

open class AddPasswordActivity : BaseActivity<ActivityAddBinding>() {

    var color: Int = 0xFF006785.toInt() // default color if nothing is selected

    private val viewModel:PasswordViewModel<PasswordRoomEntity> by viewModels {
        PasswordViewModelFactory(RoomDatabaseRepositoryImpl(roomDatabaseDao))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityAddBinding.inflate(layoutInflater))

        setSupportActionBar(binding.inc.toolbar)
        binding.inc.collapse.title = getString(R.string.add_password)
        binding.inc.toolbar.setNavigationOnClickListener { finish() }
        binding.imgPreview.setOnClickListener { showColorPickerDialog() }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add) {
            saveData(null)  // null because we use @PrimaryKey(autoGenerate = true) in the room db
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showColorPickerDialog(){

        ColorPickerDialog.Builder(this)
            .setTitle(getString(R.string.pick_color))
            .setPositiveButton(getString(R.string.confirm_ok),object : ColorEnvelopeListener {

                override fun onColorSelected(envelope: ColorEnvelope, fromUser: Boolean) {
                    if (fromUser){
                        binding.edtColor.setText("#${envelope.hexCode}")
                        binding.imgPreview.setBackgroundColor(envelope.color)
                        color = envelope.color
                    }
                }

            })
            .setNegativeButton(R.string.confirm_close){ dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setBottomSpace(12)
            .show()

    }


    protected open fun saveData(id:Long?) {

        val title = binding.edtTitle.text.toString().trim()
        val username = binding.edtUsername.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val rePassword = binding.edtRePassword.text.toString().trim()
        val description = binding.edtDesc.text.toString().trim()
        val tags = binding.edtTags.text.toString().trim()

        if (username.isNotBlank() && password.isNotBlank() && rePassword.isNotBlank() && description.isNotBlank() && tags.isNotBlank()) {

            if (password != rePassword) {
                Toast.makeText(this,getString(R.string.input_pass_not_equals),Toast.LENGTH_LONG).show()
            } else {
                viewModel.upsert(PasswordRoomEntity(title,username,password,description,color,tags,getCurrentLocaleTime(),id))
                Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
                finish()
            }

        } else {
            Toast.makeText(this,getString(R.string.input_cannot_empty), Toast.LENGTH_SHORT).show()
        }
    }

}
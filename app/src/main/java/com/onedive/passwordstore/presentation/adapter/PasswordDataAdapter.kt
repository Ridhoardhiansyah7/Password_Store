package com.onedive.passwordstore.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.onedive.passwordstore.data.dataSource.local.room.entity.PasswordRoomDatabaseEntity
import com.onedive.passwordstore.databinding.PasswordItemBinding

class PasswordDataAdapter(
    private val list: List<PasswordRoomDatabaseEntity>,
    private val onClick : (Int) -> Unit,
    private val onLongClick : (Int) -> Unit,
    context: Context,
) : BaseAdapter<PasswordItemBinding>(context) {

    override fun createBinding(parent: ViewGroup, viewType: Int): PasswordItemBinding {
        return PasswordItemBinding.inflate(LayoutInflater.from(context),parent,false)
    }


    @SuppressLint("NewApi")
    override fun bindVh(holder: BaseHolder<PasswordItemBinding>, position: Int) {
        with(list[position]){

            with(binding){

                itemTitleSingle.text = title.first().uppercase()
                itemTitle.text = title
                itemDesc.text = desc

                itemImage.setBackgroundColor(roundedColor)
                holder.itemView.setOnClickListener { onClick(position) }
                holder.itemView.setOnLongClickListener {
                    onLongClick(position)
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

}
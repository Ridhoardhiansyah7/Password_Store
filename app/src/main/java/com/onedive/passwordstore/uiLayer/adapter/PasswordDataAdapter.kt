package com.onedive.passwordstore.uiLayer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.onedive.passwordstore.databinding.PasswordItemBinding
import com.onedive.passwordstore.domainLayer.dataSource.room.entity.PasswordRoomEntity

class PasswordDataAdapter(
    private val list: List<PasswordRoomEntity>,
    private val onClick : (Int) -> Unit,
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
            }
        }
    }

    override fun getItemCount(): Int = list.size

}
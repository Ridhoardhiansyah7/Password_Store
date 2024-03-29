package com.onedive.passwordstore.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onedive.passwordstore.databinding.PasswordItemBinding
import com.onedive.passwordstore.domain.model.DatabaseModelDTO

class PasswordDataAdapter(
    private val list: List<DatabaseModelDTO>,
    private val onClick : (Int) -> Unit,
    private val onLongClick : (Int,View) -> Unit,
    context: Context,
) : BaseAdapter<PasswordItemBinding>(context) {

    override fun createBinding(parent: ViewGroup, viewType: Int): PasswordItemBinding {
        return PasswordItemBinding.inflate(LayoutInflater.from(context),parent,false)
    }


    override fun bindVh(holder: BaseHolder<PasswordItemBinding>, position: Int) {
        with(list[position]){

            with(binding){

                itemTitleSingle.text = title.first().uppercase()
                itemTitle.text = title
                itemDesc.text = desc

                itemImage.setBackgroundColor(roundedColor)
                holder.itemView.setOnClickListener { onClick(position) }
                holder.itemView.setOnLongClickListener {
                    onLongClick(position,holder.itemView)
                    true
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

}
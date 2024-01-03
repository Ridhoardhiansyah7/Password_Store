package com.onedive.passwordstore.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.onedive.passwordstore.databinding.TagItemBinding

class PasswordDataTypeAdapter(
    private val tagName:List<String>,
    private val onClick: (Int) -> Unit,
    context: Context,
) : BaseAdapter<TagItemBinding>(context) {

    override fun createBinding(parent: ViewGroup, viewType: Int): TagItemBinding {
        return TagItemBinding.inflate(LayoutInflater.from(context),parent,false)
    }

    override fun bindVh(holder: BaseHolder<TagItemBinding>, position: Int) {
        binding.chip.text = tagName[position]
        holder.itemView.setOnClickListener { onClick(position) }
    }

    override fun getItemCount() = tagName.size

}
package br.com.pixelwolf.wolfpack

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class WolfpackViewHolder<ItemType, out BindingType : ViewBinding>(
    binding: BindingType
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: ItemType)
}

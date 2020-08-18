package br.com.pixelwolf.wolfpack

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class WolfpackViewHolder<out BindingType : ViewBinding> internal constructor(
    val binding: BindingType
) : RecyclerView.ViewHolder(binding.root)

package br.com.pixelwolf.wolfpack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import br.com.pixelwolf.wolfpack.config.ViewType

class WolfpackAdapter<ItemType>(
    private val config: WolfpackConfig<ItemType>
) : ListAdapter<ItemType, WolfpackViewHolder<ItemType, ViewBinding>>(config.diff) {

    override fun getItemViewType(position: Int): Int {
        if (config.viewTypeRegisters.isEmpty) return ViewType.NONE
        return config.viewTypeRegisters
            .filter { register -> register.value(getItem(position), position) }
            .keys
            .first()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WolfpackViewHolder<ItemType, ViewBinding> {
        val holder = config.viewHolderRegisters[viewType]
            ?: error("No View Holders are registered for the View Type: $viewType")

        return holder(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun onBindViewHolder(
        holder: WolfpackViewHolder<ItemType, ViewBinding>,
        position: Int
    ) = holder.bind(getItem(position))
}

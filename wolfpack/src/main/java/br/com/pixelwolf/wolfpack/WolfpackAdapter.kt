package br.com.pixelwolf.wolfpack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import br.com.pixelwolf.wolfpack.config.ViewType

class WolfpackAdapter<ItemType>(
    private val config: WolfpackConfig<ItemType>
) : ListAdapter<ItemType, WolfpackViewHolder<ViewBinding>>(config.diff) {

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
    ): WolfpackViewHolder<ViewBinding> {
        val binding = config.viewHolderRegisters[viewType]
            ?: error("No View Holders are registered for the View Type: $viewType")

        return WolfpackViewHolder(
            binding(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: WolfpackViewHolder<ViewBinding>,
        position: Int
    ) {
        val viewType = getItemViewType(position)
        val onBindViewHolder = config.onBindViewHolderRegisters[viewType]
            ?: error("No View Holder Binders are registered for the View Type: $viewType")

        onBindViewHolder(holder.binding, getItem(position), position)
    }
}

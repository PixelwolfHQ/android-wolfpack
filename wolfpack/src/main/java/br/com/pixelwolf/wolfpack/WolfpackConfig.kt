package br.com.pixelwolf.wolfpack

import androidx.collection.ArrayMap
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import br.com.pixelwolf.wolfpack.config.Bindable
import br.com.pixelwolf.wolfpack.config.DiffConfig
import br.com.pixelwolf.wolfpack.config.DiffConfigImpl
import br.com.pixelwolf.wolfpack.config.ViewHolderConfig
import br.com.pixelwolf.wolfpack.config.ViewHolderConfigImpl
import br.com.pixelwolf.wolfpack.config.ViewType

class WolfpackConfig<ItemType> private constructor(
    val diff: DiffUtil.ItemCallback<ItemType>,
    val viewTypeRegisters: ArrayMap<Int, (item: ItemType, position: Int) -> Boolean>,
    val viewHolderRegisters: ArrayMap<Int, Bindable<ItemType, out ViewBinding>>
) {

    class Builder<ItemType> {

        private val diffConfig: DiffConfig<ItemType> = DiffConfigImpl()
        private val viewHolderConfig: ViewHolderConfig<ItemType> = ViewHolderConfigImpl()

        fun diff(
            areItemsTheSame: (oldItem: ItemType, newItem: ItemType) -> Boolean,
            areContentsTheSame: (oldItem: ItemType, newItem: ItemType) -> Boolean
        ) = diffConfig.diff(areItemsTheSame, areContentsTheSame)

        fun <BindingType : ViewBinding> viewHolder(
            viewHolder: Bindable<ItemType, BindingType>,
            viewType: ViewType<ItemType>? = null
        ) = viewHolderConfig.viewHolder(viewHolder, viewType)

        fun build() = WolfpackConfig(
            diffConfig.diff,
            viewHolderConfig.viewTypeRegisters,
            viewHolderConfig.viewHolderRegisters
        )
    }
}

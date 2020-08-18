package br.com.pixelwolf.wolfpack

import androidx.collection.ArrayMap
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import br.com.pixelwolf.wolfpack.config.Bindable
import br.com.pixelwolf.wolfpack.config.DiffConfig
import br.com.pixelwolf.wolfpack.config.DiffConfigImpl
import br.com.pixelwolf.wolfpack.config.OnBind
import br.com.pixelwolf.wolfpack.config.ViewHolderConfig
import br.com.pixelwolf.wolfpack.config.ViewHolderConfigImpl
import br.com.pixelwolf.wolfpack.config.ViewType

class WolfpackConfig<ItemType> private constructor(
    val diff: DiffUtil.ItemCallback<ItemType>,
    val viewTypeRegisters: ArrayMap<Int, (item: ItemType, position: Int) -> Boolean>,
    val viewHolderRegisters: ArrayMap<Int, Bindable<out ViewBinding>>,
    val onBindRegisters: ArrayMap<Int, OnBind<ItemType, ViewBinding>>
) {

    class Builder<ItemType> {

        private val diffConfig: DiffConfig<ItemType> = DiffConfigImpl()
        private val viewHolderConfig: ViewHolderConfig<ItemType> = ViewHolderConfigImpl()

        fun diff(
            areItemsTheSame: (oldItem: ItemType, newItem: ItemType) -> Boolean,
            areContentsTheSame: (oldItem: ItemType, newItem: ItemType) -> Boolean
        ) = diffConfig.diff(areItemsTheSame, areContentsTheSame)

        fun <BindingType : ViewBinding, ItemSubType : ItemType> viewHolder(
            binding: Bindable<BindingType>,
            onBindViewHolder: (BindingType, ItemSubType, Int) -> Unit,
            viewType: ViewType<ItemType>? = null
        ) = viewHolderConfig.viewHolder(binding, onBindViewHolder, viewType)

        fun build(): WolfpackConfig<ItemType> = WolfpackConfig(
            diffConfig.diff,
            viewHolderConfig.viewTypeRegisters,
            viewHolderConfig.bindingRegisters,
            viewHolderConfig.onBindRegisters
        )
    }
}

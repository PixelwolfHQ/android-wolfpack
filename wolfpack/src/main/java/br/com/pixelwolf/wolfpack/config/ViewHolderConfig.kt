package br.com.pixelwolf.wolfpack.config

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
import androidx.viewbinding.ViewBinding
import br.com.pixelwolf.wolfpack.WolfpackViewHolder

internal typealias Bindable<ItemType, BindingType> = (
    LayoutInflater,
    ViewGroup,
    Boolean
) -> WolfpackViewHolder<ItemType, BindingType>

internal interface ViewHolderConfig<ItemType> {
    val viewHolderRegisters: ArrayMap<Int, Bindable<ItemType, out ViewBinding>>
    val viewTypeRegisters: ArrayMap<Int, (item: ItemType, position: Int) -> Boolean>

    fun <BindingType : ViewBinding> viewHolder(
        viewHolder: Bindable<ItemType, BindingType>,
        viewType: ViewType<ItemType>? = null
    )
}

internal class ViewHolderConfigImpl<ItemType> : ViewHolderConfig<ItemType> {

    private val innerViewHolderRegisters = arrayMapOf<Int, Bindable<ItemType, out ViewBinding>>()

    override val viewHolderRegisters: ArrayMap<Int, Bindable<ItemType, out ViewBinding>>
        get() {
            if (innerViewHolderRegisters.isEmpty) error("At least one View Holder must be registered")
            return innerViewHolderRegisters
        }

    override val viewTypeRegisters = arrayMapOf<Int, (item: ItemType, position: Int) -> Boolean>()

    override fun <BindingType : ViewBinding> viewHolder(
        viewHolder: Bindable<ItemType, BindingType>,
        viewType: ViewType<ItemType>?
    ) {
        val viewTypeId = viewType?.id ?: ViewType.NONE

        this.innerViewHolderRegisters[viewTypeId] = viewHolder
        viewType?.rule.let { rule -> this.viewTypeRegisters[viewTypeId] = rule }
    }
}

data class ViewType<ItemType>(
    val id: Int,
    val rule: (item: ItemType, position: Int) -> Boolean
) {

    companion object {
        const val NONE = -9999
    }
}

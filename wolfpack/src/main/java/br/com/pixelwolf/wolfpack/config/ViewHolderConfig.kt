package br.com.pixelwolf.wolfpack.config

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.ArrayMap
import androidx.collection.arrayMapOf
import androidx.viewbinding.ViewBinding

internal typealias Bindable<BindingType> = (
    inflater: LayoutInflater,
    parent: ViewGroup,
    attachToParent: Boolean
) -> BindingType

internal typealias OnBind<ItemType, BindingType> = (
    binding: BindingType,
    item: ItemType,
    position: Int
) -> Unit

internal interface ViewHolderConfig<ItemType> {
    val bindingRegisters: ArrayMap<Int, Bindable<out ViewBinding>>
    val onBindRegisters: ArrayMap<Int, (ViewBinding, ItemType, Int) -> Unit>
    val viewTypeRegisters: ArrayMap<Int, (item: ItemType, position: Int) -> Boolean>

    fun <BindingType : ViewBinding, ItemSubType : ItemType> viewHolder(
        binding: Bindable<BindingType>,
        onBind: (BindingType, ItemSubType, Int) -> Unit,
        viewType: ViewType<ItemType>? = null
    )
}

internal class ViewHolderConfigImpl<ItemType> : ViewHolderConfig<ItemType> {

    private val innerBindingRegisters = arrayMapOf<Int, Bindable<out ViewBinding>>()
    override val bindingRegisters: ArrayMap<Int, Bindable<out ViewBinding>>
        get() {
            if (innerBindingRegisters.isEmpty) error("At least one View Holder must be registered")
            return innerBindingRegisters
        }

    private val innerOnBindRegisters = arrayMapOf<Int, (ViewBinding, ItemType, Int) -> Unit>()
    override val onBindRegisters: ArrayMap<Int, (ViewBinding, ItemType, Int) -> Unit>
        get() {
            if (innerOnBindRegisters.isEmpty) error("At least one View Holder Binder must be registered")
            return innerOnBindRegisters
        }

    override val viewTypeRegisters = arrayMapOf<Int, (item: ItemType, position: Int) -> Boolean>()

    override fun <BindingType : ViewBinding, ItemSubType : ItemType> viewHolder(
        binding: Bindable<BindingType>,
        onBind: (BindingType, ItemSubType, Int) -> Unit,
        viewType: ViewType<ItemType>?
    ) {
        val viewTypeId = viewType?.id ?: ViewType.NONE

        this.innerBindingRegisters[viewTypeId] = binding

        @Suppress("UNCHECKED_CAST")
        this.innerOnBindRegisters[viewTypeId] = onBind as (ViewBinding, ItemType, Int) -> Unit

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

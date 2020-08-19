package br.com.pixelwolf.wolfpack.config

import androidx.recyclerview.widget.DiffUtil

internal interface DiffConfig<ItemType> {
    val diff: DiffUtil.ItemCallback<ItemType>

    fun diff(
        areItemsTheSame: (oldItem: ItemType, newItem: ItemType) -> Boolean,
        areContentsTheSame: (oldItem: ItemType, newItem: ItemType) -> Boolean
    )
}

internal class DiffConfigImpl<ItemType> : DiffConfig<ItemType> {

    private var innerDiff: DiffUtil.ItemCallback<ItemType>? = null

    override val diff: DiffUtil.ItemCallback<ItemType>
        get() = innerDiff ?: error("Diff must be set.")

    override fun diff(
        areItemsTheSame: (oldItem: ItemType, newItem: ItemType) -> Boolean,
        areContentsTheSame: (oldItem: ItemType, newItem: ItemType) -> Boolean
    ) {
        this.innerDiff = object : DiffUtil.ItemCallback<ItemType>() {
            override fun areItemsTheSame(oldItem: ItemType, newItem: ItemType): Boolean =
                areItemsTheSame(oldItem, newItem)

            override fun areContentsTheSame(oldItem: ItemType, newItem: ItemType): Boolean =
                areContentsTheSame(oldItem, newItem)
        }
    }
}

# Wolfpack :wolf: :wolf: :wolf:

- Kotlin-first
- ViewBinding-first
- Boiler Plate-free
- Easy-to-use DSL

Tired of implementing the same bloated code of the **RecyclerView Adapters** over and over each time we need to handle a list in Android, we decided to create this library which omits the creation of an adapter and eases the creation of ViewHolders.

**Wolfpack** follows the good practices of Android and development and uses the latest dependencies.

##### **Alert:** At the moment it is not possible to use Layout Resources instead of ViewBinding. [@ibrahimyilmaz](https://github.com/ibrahimyilmaz) has a solution named [Kiel](https://github.com/ibrahimyilmaz/kiel) that uses them.

---

## Simple Usage

```kotlin
val adapter = adapterFor<Recipe> {
    viewHolder(
        viewHolder = { inflater, parent, attachToParent ->
            RecipeViewHolder(
                RecipeBinding.inflate(
                    inflater,
                    parent,
                    attachToParent
                )
            )
        }
    )
    diff(
        areItemsTheSame = { oldItem, newItem -> oldItem == newItem },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
    build()
}

// A anonymous class could be used instead this one
class RecipeViewHolder(
    private val binding: RecipeBinding
) : WolfpackViewHolder<Recipe, RecipeBinding>(
    binding
) {
    override fun bind(item: Recipe, position: Int) {
        binding.apply {
            image.load(item.photoUrl)
            description.text = item.description
        }
    }
}
```

<details>
<summary>Vanilla implementation</summary>

```kotlin
class RecipeAdapter : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Recipe) {
            binding.apply {
            image.load(item.photoUrl)
            description.text = item.description
        }
    }

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean = oldItem.id == newItem.id
        }
    }

}

```

</details>

---

## Multiple View Holders and View Types

```kotlin
val adapter = adapterFor<Vehicle> {
    viewHolder(
        viewHolder = { inflater, parent, attachToParent ->
            CarViewHolder(
                CarBinding.inflate(
                    inflater,
                    parent,
                    attachToParent
                )
            )
        },
        viewType = ViewType(
            id = 1,
            rule = { item, _ -> item is Car }
        )
    )
    viewHolder(
        viewHolder = { inflater, parent, attachToParent ->
            AirplaneViewHolder(
                AirplaneBinding.inflate(
                    inflater,
                    parent,
                    attachToParent
                )
            )
        },
        viewType = ViewType(
            id = 2,
            rule = { item, _ -> item is Airplane }
        )
    )
    diff(
        areItemsTheSame = { oldItem, newItem -> oldItem == newItem },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
    build()
}

class CarViewHolder(
    private val binding: CarBinding
) : WolfpackViewHolder<Car, CarBinding>(
    binding
) {
    override fun bind(item: Car, position: Int) {
        binding.apply {
            image.load(item.photoUrl)
            name.text = item.name
            horsepower.text = item.horsepower
        }
    }
}

class AirplaneViewHolder(
    private val binding: AirplaneBinding
) : WolfpackViewHolder<Airplane, AirplaneBinding>(
    binding
) {
    override fun bind(item: Airplane, position: Int) {
        binding.apply {
            image.load(item.photoUrl)
            name.text = item.name
            seats.text = item.seats
        }
    }
}
```

<details>
<summary>Vanilla implementation</summary>

```kotlin
class VehicleAdapter : ListAdapter<Vehicle, RecyclerView.ViewHolder>(
    DIFF_CALLBACK
) {

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is Car -> 1
        is Airplane -> 2
        else -> error("No condition found for item at position: $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            1 -> CarViewHolder(
                CarBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            2 -> AirplaneViewHolder(
                AirplaneBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> error("View Holder not found for View Type: $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarViewHolder(
        private val binding: CarBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Car) {
            binding.apply {
                image.load(item.photoUrl)
                name.text = item.name
                horsepower.text = item.horsepower
            }
        }
    }

    inner class AirplaneViewHolder(
        private val binding: AirplaneBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Airplane) {
            binding.apply {
                image.load(item.photoUrl)
                name.text = item.name
                seats.text = item.seats
            }
        }
    }

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Vehicle>() {
            override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean = oldItem == newItem
        }
    }
}

```

</details>

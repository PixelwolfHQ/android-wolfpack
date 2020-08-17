# Wolfpack :wolf: :wolf: :wolf:

- Kotlin-first
- ViewBinding-first
- Verbose-free
- Easy-to-use DSL

Tired of implementing the same bloated code of the **RecyclerView** Adapters over and over each time we need to handle a list in Android, we decided to create this library which omits the creation of an adapter.

**Wolfpack** follows the good practices of Android and development and uses the latest dependencies.

##### **Alert:** At the moment it is not possible to use Layout Resources instead of ViewBinding. [@ibrahimyilmaz](https://github.com/ibrahimyilmaz) has a solution named [Kiel](https://github.com/ibrahimyilmaz/kiel) that uses them.

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

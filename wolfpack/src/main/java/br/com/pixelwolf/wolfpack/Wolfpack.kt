package br.com.pixelwolf.wolfpack

fun <ItemType> adapterFor(
    buildConfig: WolfpackConfig.Builder<ItemType>.() -> WolfpackConfig<ItemType>
) = WolfpackAdapter(
    buildConfig(WolfpackConfig.Builder())
)

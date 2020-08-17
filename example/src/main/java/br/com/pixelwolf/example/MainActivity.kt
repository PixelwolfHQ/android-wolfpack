package br.com.pixelwolf.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pixelwolf.example.databinding.MainActivityBinding
import br.com.pixelwolf.example.databinding.WolfpackExample1Binding
import br.com.pixelwolf.example.databinding.WolfpackExample2Binding
import br.com.pixelwolf.example.databinding.WolfpackExample3Binding
import br.com.pixelwolf.wolfpack.WolfpackConfig
import br.com.pixelwolf.wolfpack.WolfpackViewHolder
import br.com.pixelwolf.wolfpack.adapterFor
import br.com.pixelwolf.wolfpack.config.ViewType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        binding.apply {
            list.apply {
                this.layoutManager = LinearLayoutManager(context)
                this.adapter = exampleAdapter.also {
                    it.submitList(
                        listOf(
                            Example1("example 1 - 1"),
                            Example2("example 2 - 1"),
                            Example3("example 3 - 1"),
                            Example1("example 1 - 2"),
                            Example2("example 2 - 2"),
                            Example3("example 3 - 2")
                        )
                    )
                }
            }
        }
        setContentView(binding.root)
    }
}

private interface Example

private data class Example1(val value: String) : Example
private data class Example2(val value: String) : Example
private data class Example3(val value: String) : Example

private class Example1ViewHolder(
    private val binding: WolfpackExample1Binding
) : WolfpackViewHolder<Example, WolfpackExample1Binding>(
    binding
) {
    override fun bind(item: Example) {
        item as Example1
        binding.example1.text = item.value
    }
}

private class Example2ViewHolder(
    private val binding: WolfpackExample2Binding
) : WolfpackViewHolder<Example, WolfpackExample2Binding>(
    binding
) {
    override fun bind(item: Example) {
        item as Example2
        binding.example2.text = item.value
    }
}

private class Example3ViewHolder(
    private val binding: WolfpackExample3Binding
) : WolfpackViewHolder<Example, WolfpackExample3Binding>(
    binding
) {
    override fun bind(item: Example) {
        item as Example3
        binding.example3.text = item.value
    }
}

private val exampleAdapter = adapterFor<Example> {
    viewHolder(
        viewHolder = { inflater, parent, attachToParent ->
            Example1ViewHolder(WolfpackExample1Binding.inflate(inflater, parent, attachToParent))
        },
        viewType = ViewType(
            id = 1,
            rule = { item, _ -> item is Example1 }
        )
    )
    viewHolder(
        viewHolder = { inflater, parent, attachToParent ->
            Example2ViewHolder(WolfpackExample2Binding.inflate(inflater, parent, attachToParent))
        },
        viewType = ViewType(
            id = 2,
            rule = { item, _ -> item is Example2 }
        )
    )
    viewHolder(
        viewHolder = { inflater, parent, attachToParent ->
            Example3ViewHolder(WolfpackExample3Binding.inflate(inflater, parent, attachToParent))
        },
        viewType = ViewType(
            id = 3,
            rule = { item, _ -> item is Example3 }
        )
    )
    diff(
        areItemsTheSame = { oldItem, newItem -> oldItem == newItem },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
    build()
}
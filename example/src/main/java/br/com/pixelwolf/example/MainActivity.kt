package br.com.pixelwolf.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pixelwolf.example.databinding.MainActivityBinding
import br.com.pixelwolf.example.databinding.WolfpackExample1Binding
import br.com.pixelwolf.example.databinding.WolfpackExample2Binding
import br.com.pixelwolf.example.databinding.WolfpackExample3Binding
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

private val exampleAdapter = adapterFor<Example> {
    viewHolder<Example1, WolfpackExample1Binding>(
        binding = WolfpackExample1Binding::inflate,
        onBindViewHolder = { binding, item, _ -> binding.example1.text = item.value },
        viewType = ViewType(id = 1, rule = { item, _ -> item is Example1 })
    )
    viewHolder<Example2, WolfpackExample2Binding>(
        binding = WolfpackExample2Binding::inflate,
        onBindViewHolder = { binding, item, _ -> binding.example2.text = item.value },
        viewType = ViewType(id = 2, rule = { item, _ -> item is Example2 })
    )
    viewHolder<Example3, WolfpackExample3Binding>(
        binding = WolfpackExample3Binding::inflate,
        onBindViewHolder = { binding, item, _ -> binding.example3.text = item.value },
        viewType = ViewType(id = 3, rule = { item, _ -> item is Example3 })
    )
    diff(
        areItemsTheSame = { oldItem, newItem -> oldItem == newItem },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
    build()
}
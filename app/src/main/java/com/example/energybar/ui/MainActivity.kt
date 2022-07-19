package com.example.energybar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.energybar.database.ContentApplication
import com.example.energybar.ContentViewModel
import com.example.energybar.WordViewModelFactory
import com.example.energybar.adapter.Adapter
import com.example.energybar.databinding.ActivityMainBinding
import com.example.energybar.model.Content

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: Adapter
    private val contentViewModel: ContentViewModel by viewModels() {
        WordViewModelFactory((application as ContentApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)

        contentViewModel.allWords.observe(this@MainActivity) {
//            Toast.makeText(this@MainActivity,it.toString(),Toast.LENGTH_SHORT).show()
                layoutManager = LinearLayoutManager(this)
                binding.seekBarRecycler.layoutManager = layoutManager
                adapter = Adapter(it as ArrayList<Content>, this)
                binding.seekBarRecycler.adapter = adapter
                adapter.notifyDataSetChanged()

        }
        setContentView(binding.root)
    }

    override fun onPause() {
        super.onPause()
        contentViewModel.deleteAll()
        contentViewModel.insert(adapter.data)

    }
}
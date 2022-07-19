package com.example.energybar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.energybar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter:Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        var data = arrayListOf<seekbar>(
            seekbar(1,25), seekbar(26,50), seekbar(51,75), seekbar(76,100)
        )
        layoutManager = LinearLayoutManager(this)
        binding.seekBarRecycler.layoutManager = layoutManager
        adapter = Adapter(data,this)
        binding.seekBarRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
        setContentView(binding.root)
    }
}
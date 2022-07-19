package com.example.energybar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.energybar.R
import com.example.energybar.databinding.CardViewBinding
import com.example.energybar.model.Content


class Adapter(var data: ArrayList<Content>, var context: Context) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(val binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cardViewLecturesBinding: CardViewBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.card_view, parent, false
        )
        return ViewHolder(cardViewLecturesBinding)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.seekBar.min = data[holder.adapterPosition].start
        holder.binding.seekBar.max = data[holder.adapterPosition].End
        holder.binding.seekBar.setProgress(data[holder.adapterPosition].End)
        holder.binding.dustbin.visibility = View.INVISIBLE
        holder.binding.cardView.setBackgroundColor(Color.parseColor(data[holder.adapterPosition].color))
        holder.binding.cardView2.setBackgroundColor(Color.parseColor(data[holder.adapterPosition].color))
        holder.binding.start.text = data[holder.adapterPosition].start.toString()
        holder.binding.end.text = data[holder.adapterPosition].End.toString()

        holder.binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                holder.binding.end.text = progress.toString()
                holder.binding.dustbin.visibility = View.VISIBLE
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                if (seekBar!!.progress == seekBar.min) {
                    when {
                        seekBar.progress == 1 -> {
                            data.clear()
                            data.add(Content(1, 100,"#e84c3d"))
                            notifyDataSetChanged()
                        }
                        else -> {
                            data[holder.adapterPosition - 1].End = seekBar!!.max
                            data.removeAt(holder.adapterPosition)
                            notifyDataSetChanged()
                        }
                    }
                }
                    else if ((seekBar.max - seekBar.progress) > 1 && (seekBar.progress - seekBar.min) > 0)  {
                        data.add(
                            holder.adapterPosition + 1,
                            Content(seekBar.progress + 1, seekBar.max,data[holder.adapterPosition].color)
                        )
                        data[holder.adapterPosition].End = seekBar.progress
                        notifyDataSetChanged()
                    }
                else  {
                    Toast.makeText(context, "Minimum segment length is 2!", Toast.LENGTH_SHORT)
                        .show()
                    notifyDataSetChanged()
                }

            }
        })
    }
}
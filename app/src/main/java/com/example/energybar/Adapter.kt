package com.example.energybar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.energybar.databinding.CardViewBinding


class Adapter(var data: ArrayList<seekbar>, var context: Context) :
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

    interface ClickListener {
        fun OnClick(position: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.seekBar.min = data[holder.adapterPosition].start
        holder.binding.seekBar.max = data[holder.adapterPosition].End
        holder.binding.seekBar.setProgress(data[holder.adapterPosition].End)
        holder.binding.dustbin.visibility = View.INVISIBLE
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
                            data.add(seekbar(1, 100))
                            notifyDataSetChanged()
                        }
                        else -> {
                            data[holder.adapterPosition - 1].End = seekBar!!.max
                            data.removeAt(holder.adapterPosition)
                            notifyDataSetChanged()
                        }
                    }
                }
                if ((seekBar.progress - seekBar.min) < 2) {
                    Toast.makeText(context, "Minimum segment length is 2!", Toast.LENGTH_SHORT)
                        .show()
                    notifyDataSetChanged()
                } else {
                    if (seekBar.progress != seekBar.max-1) {
                        data.add(
                            holder.adapterPosition + 1,
                            seekbar(seekBar.progress + 1,seekBar.max)
                        )
                        data[holder.adapterPosition].End = seekBar.progress
                        notifyDataSetChanged()
                    }
                    else
                    {seekBar.progress=seekBar.max
                    notifyDataSetChanged()}
                }
            }
        })
    }
}
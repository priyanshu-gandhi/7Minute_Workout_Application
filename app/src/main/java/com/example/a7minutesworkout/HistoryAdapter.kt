package com.example.a7minutesworkout

import android.view.LayoutInflater
import android.view.ViewGroup
//import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import com.example.a7minutesworkout.databinding.ItemHistoryBinding
import android.graphics.Color


class HistoryAdapter(private var items : ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root)
    {
        val llHistoryItemMain =binding.llHistoryItemMain
        val tvtext = binding.tvtext
        val tvDate = binding.tvdate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {

        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val date : String = items.get(position)
        holder.tvtext.text= (position+1).toString()
        holder.tvDate.text= date

        if(position %2==0)
        {
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#EBEBEB"))
        }
        else
        {
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }
}



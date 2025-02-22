package com.example.a7minutesworkout.ui.theme

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.ExerciseModel
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>):RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


    class ViewHolder(binding: ItemExerciseStatusBinding): RecyclerView.ViewHolder(binding.root)
    {
            val tvItem =binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val model :ExerciseModel= items[position]
        holder.tvItem.text = model.getId().toString()

       when{
           model.getisSelected()->
           {
               holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_thin_border_background)
               holder.tvItem.setTextColor(Color.parseColor("#212121"))

           }
           model.getisCompleted()->
           {
               holder.tvItem.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_color_accent_background)
               holder.tvItem.setTextColor(Color.parseColor("#ffff"))
           }
           else ->
           {
               holder.tvItem.background =ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_grey_background)
               holder.tvItem.setTextColor((Color.parseColor("#212121")))

           }
       }

    }


}
package com.example.trainer_helper_and_eat_supplements.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.Database.Data.TakingTimeData
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.SelectManyExerciseItemBinding
import com.example.trainer_helper_and_eat_supplements.databinding.TimeOfTakingFoodAdditiveItemBinding

class TimeOfTakingFoodAdapter(
    exerciseNames:MutableList<TakingTimeData>,
    datamodel: MyDataModel,
    context: Context
): RecyclerView.Adapter<TimeOfTakingFoodAdapter.TimeOfTakingFoodHolder>() {
    val allExercisesNames = exerciseNames
    val curDataModel = datamodel
    val curContext = context

    class TimeOfTakingFoodHolder(
        item: View,
        holdDatamodel:MyDataModel,
        holdContext:Context
    ): RecyclerView.ViewHolder(item){
        fun bind(data:TakingTimeData, pos:Int){
            
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeOfTakingFoodHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_of_taking_food_additive_item, parent, false)

        val binding = TimeOfTakingFoodAdditiveItemBinding.bind(view)
        return TimeOfTakingFoodAdapter.TimeOfTakingFoodHolder(
            binding.root,
            curDataModel,
            curContext
        )
    }

    override fun onBindViewHolder(holder: TimeOfTakingFoodHolder, position: Int) {
        holder.bind(allExercisesNames.get(position), position)
    }

    override fun getItemCount(): Int {
        return allExercisesNames.size
    }
}
package com.example.trainer_helper_and_eat_supplements.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.CONSTANTS
import com.example.trainer_helper_and_eat_supplements.Database.Data.TakingTimeData
import com.example.trainer_helper_and_eat_supplements.EditAddExerciseActivity
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.MainListItemBinding
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
        var parent: ViewGroup,
        holdDatamodel:MyDataModel,
        holdContext:Context
    ): RecyclerView.ViewHolder(item){
        var binding = MainListItemBinding.bind(item)
        val myDataModel = holdDatamodel
        val curContext = holdContext

        fun bind(data:TakingTimeData, pos:Int){
            binding.imageButton.setOnClickListener(){
                val popupMenu = PopupMenu(parent.context,it)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_delete_btn -> {
                            // TODO удаление из ExerciseMesure
                            Log.d("MyLog", "delete")
                        }
                        R.id.menu_edit_btn ->{
                            Log.d("MyLog", "edit")
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeOfTakingFoodHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_of_taking_food_additive_item, parent, false)

        val binding = TimeOfTakingFoodAdditiveItemBinding.bind(view)
        return TimeOfTakingFoodAdapter.TimeOfTakingFoodHolder(
            binding.root,
            parent,
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
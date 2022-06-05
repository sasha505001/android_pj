package com.example.trainer_helper_and_eat_supplements.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.TakingTimeData
import com.example.trainer_helper_and_eat_supplements.databinding.MainListItemBinding
import com.example.trainer_helper_and_eat_supplements.databinding.SelectManyExerciseItemBinding
import com.example.trainer_helper_and_eat_supplements.databinding.TimeOfTakingFoodAdditiveItemBinding
import java.text.SimpleDateFormat

class TimeOfTakingFoodAdapter(
    takingTimes:MutableList<TakingTimeData>,
    datamodel: MyDataModel,
    context: Context,
    allTimeOfTaking:MutableList<TakingTimeData>

): RecyclerView.Adapter<TimeOfTakingFoodAdapter.TimeOfTakingFoodHolder>() {
    val allTakingTimes = takingTimes
    val curDataModel = datamodel
    val curContext = context
    var curAllTimeOfTaking = allTimeOfTaking
    init {
        curDataModel.allTakingTimeForFoodAdditive.observe(curContext as LifecycleOwner){
            curAllTimeOfTaking = it
        }
    }

    class TimeOfTakingFoodHolder(
        item: View,
        var parent: ViewGroup,
        holdDatamodel:MyDataModel,
        holdContext:Context,
        allTimeOfTaking:MutableList<TakingTimeData>
    ): RecyclerView.ViewHolder(item){
        var binding = TimeOfTakingFoodAdditiveItemBinding.bind(item)
        val myDataModel = holdDatamodel
        val curContext = holdContext
        var curAllTimeOfTaking = allTimeOfTaking

        init{
            myDataModel.allTakingTimeForFoodAdditive.observe(curContext as LifecycleOwner){
                curAllTimeOfTaking = it
            }
        }

        fun bind(data:TakingTimeData, pos:Int){
            binding.countText.setText(data.dose_taken.toString())
            val dataFormatter = SimpleDateFormat("HH:mm")
            val resString = dataFormatter.format(data.taking_time.time)
            binding.timeText.setText(resString)
            binding.btnPopupMenu.setOnClickListener(){
                val popupMenu = PopupMenu(parent.context,it)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_delete_btn -> {
                            curAllTimeOfTaking.removeAt(pos)
                            myDataModel.allTakingTimeForFoodAdditive.value = curAllTimeOfTaking
                        }
                        R.id.menu_edit_btn ->{
                            val editData = curAllTimeOfTaking.get(pos)
                            editData.id = pos
                            myDataModel.editTime.value = editData
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
            curContext,
            curAllTimeOfTaking
        )
    }

    override fun onBindViewHolder(holder: TimeOfTakingFoodHolder, position: Int) {
        holder.bind(allTakingTimes.get(position), position)
    }

    override fun getItemCount(): Int {
        return allTakingTimes.size
    }
}
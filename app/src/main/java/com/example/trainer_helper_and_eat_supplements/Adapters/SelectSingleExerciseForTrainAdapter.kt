package com.example.trainer_helper_and_eat_supplements.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.*
import com.example.trainer_helper_and_eat_supplements.databinding.ItemSelectComplexForTrainBinding
import com.example.trainer_helper_and_eat_supplements.databinding.ItemSelectSingleExerciseForTrainBinding

class SelectSingleExerciseForTrainAdapter(
    selectedExerciseName:String,
    exerciseList:List<String>,
    datamodel: MyDataModel,
    context: Context
): RecyclerView.Adapter<SelectSingleExerciseForTrainAdapter.SelectSingleExerciseForTrainHolder>() {
    val allExerciseNames = exerciseList
    val curDataModel = datamodel
    val curContext = context
    // Выбранный элемент(1 из списка)
    var nameOfSelectedItem = selectedExerciseName
    var selectedItemIndex:Int = 0
    init {
        selectedItemIndex = allExerciseNames.indexOf(nameOfSelectedItem)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectSingleExerciseForTrainHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_select_single_exercise_for_train, parent, false)

        val binding = ItemSelectSingleExerciseForTrainBinding.bind(view)
        return SelectSingleExerciseForTrainHolder(
            binding.root,
            curDataModel,
            curContext
        )
    }

    override fun onBindViewHolder(holder: SelectSingleExerciseForTrainHolder, position: Int) {
        holder.bind(allExerciseNames.get(position), position)
        holder.binding.radioButton.isChecked = position == selectedItemIndex
        if(position == selectedItemIndex){
            nameOfSelectedItem = allExerciseNames.get(position)
        }
    }

    override fun getItemCount(): Int {
        return allExerciseNames.size
    }

    // viewHoleder
    inner class SelectSingleExerciseForTrainHolder(
        item: View,
        holdDatamodel:MyDataModel,
        holdContext:Context
    ): RecyclerView.ViewHolder(item){
        var binding = ItemSelectSingleExerciseForTrainBinding.bind(item)
        var curDataModel = holdDatamodel
        var curContext = holdContext

        fun bind(str:String, pos:Int){
            binding.radioButton.text = str
            binding.infoBtn.setOnClickListener(){
                val intent = Intent(curContext, ObserverOfExerciseActivity::class.java)
                intent.putExtra(CONSTANTS.NAMEOFOBSERVE, str)
                curContext.startActivity(intent)
            }

            binding.radioButton.setOnClickListener(){
                selectedItemIndex = adapterPosition
                nameOfSelectedItem = allExerciseNames.get(selectedItemIndex)
                notifyDataSetChanged()
            }
        }
    }

}
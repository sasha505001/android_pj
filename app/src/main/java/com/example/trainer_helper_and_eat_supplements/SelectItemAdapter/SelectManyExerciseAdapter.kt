package com.example.trainer_helper_and_eat_supplements.SelectItemAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListComplexAdapter
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.MainListItemBinding
import com.example.trainer_helper_and_eat_supplements.databinding.SelectManyExerciseItemBinding

class SelectManyExerciseAdapter(
    exerciseNames:List<String>,
    datamodel:MyDataModel,
    context:Context
): RecyclerView.Adapter<SelectManyExerciseAdapter.SelectManyExerciseHolder>(){
    val allExercisesNames = exerciseNames
    val curDataModel = datamodel
    val curContext = context


    class SelectManyExerciseHolder(
        item: View,
        holdDatamodel:MyDataModel,
        holdContext:Context
    ) : RecyclerView.ViewHolder(item){
        var binding = SelectManyExerciseItemBinding.bind(item)
        var curDataModel = holdDatamodel
        var curContext = holdContext

        fun bind(str:String, pos:Int){
            binding.checkBtn.setOnCheckedChangeListener(){ btn, check ->
                curDataModel.allBoolExercise.value!![pos] = check
            }
            binding.textView.setText(str)
            curDataModel.allBoolExercise.observe(curContext as LifecycleOwner){ bools->
                binding.checkBtn.isChecked = bools.get(pos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectManyExerciseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_many_exercise_item, parent, false)

        val binding = SelectManyExerciseItemBinding.bind(view)
        return SelectManyExerciseAdapter.SelectManyExerciseHolder(
            binding.root,
            curDataModel,
            curContext
        )
    }

    override fun onBindViewHolder(holder: SelectManyExerciseHolder, position: Int) {
        holder.bind(allExercisesNames.get(position), position)
    }

    override fun getItemCount(): Int {
        return allExercisesNames.size
    }
}
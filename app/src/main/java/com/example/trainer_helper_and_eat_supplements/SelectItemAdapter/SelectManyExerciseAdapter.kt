package com.example.trainer_helper_and_eat_supplements.SelectItemAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListComplexAdapter
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.MainListItemBinding
import com.example.trainer_helper_and_eat_supplements.databinding.SelectManyExerciseItemBinding

class SelectManyExerciseAdapter(
    exerciseNames:List<String>,
    //selectedExercises:List<String>
): RecyclerView.Adapter<SelectManyExerciseAdapter.SelectManyExerciseHolder>(){
    val allExercisesNames = exerciseNames

    class SelectManyExerciseHolder(
        item: View,
        //selectedExercises: List<String>?
    ) : RecyclerView.ViewHolder(item){
        var binding = SelectManyExerciseItemBinding.bind(item)

        fun bind(str:String){
            binding.textView.setText(str)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectManyExerciseHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_many_exercise_item, parent, false)

        var binding = SelectManyExerciseItemBinding.bind(view)
        return SelectManyExerciseAdapter.SelectManyExerciseHolder(
            binding.root
        )
    }

    override fun onBindViewHolder(holder: SelectManyExerciseHolder, position: Int) {
        holder.bind(allExercisesNames.get(position))
    }

    override fun getItemCount(): Int {
        return allExercisesNames.size
    }
}
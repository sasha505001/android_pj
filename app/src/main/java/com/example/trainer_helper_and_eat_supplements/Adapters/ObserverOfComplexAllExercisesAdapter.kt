package com.example.trainer_helper_and_eat_supplements.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.CONSTANTS
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.ObserverOfExerciseActivity
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.ItemExerciseFromComplexBinding

// Адаптер для отображения всех упражнений в комплексе в observeComplex
class ObserverOfComplexAllExercisesAdapter(
    myList:List<String>,
    datamodel:MyDataModel,
    context:Context):
    RecyclerView.Adapter<ObserverOfComplexAllExercisesAdapter.ObserverOfComplexAllExercisesHolder>(){
        val exercisesList:List<String> = myList
        val curDataModel = datamodel
        val curContext = context

        class ObserverOfComplexAllExercisesHolder
            (item: View,
             holdDatamodel:MyDataModel,
             holdContext: Context
        ):RecyclerView.ViewHolder(item)
        {
            var binding = ItemExerciseFromComplexBinding.bind(item)
            val curDataModel = holdDatamodel
            var curContext = holdContext

            fun bind(str:String){
                binding.nameOfExerciseText.text = str
                binding.infoOfExerciseBtn.setOnClickListener(){
                    val intent = Intent(curContext, ObserverOfExerciseActivity::class.java)
                    intent.putExtra(CONSTANTS.NAMEOFOBSERVE, str)
                    curContext.startActivity(intent)
                }
            }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ObserverOfComplexAllExercisesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_from_complex, parent, false)

        val binding = ItemExerciseFromComplexBinding.bind(view)
        return ObserverOfComplexAllExercisesHolder(
            binding.root,
            curDataModel,
            curContext
        )
    }

    override fun onBindViewHolder(holder: ObserverOfComplexAllExercisesHolder, position: Int) {
        holder.bind(exercisesList.get(position))
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

}
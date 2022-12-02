package com.example.trainer_helper_and_eat_supplements.Adapters.Approach

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.ItemApproachOfTrainBinding

class ApproachAdapter(
    apprachesOfExercise: ArrayList<HashMap<String, Float>>,
    datamodel: MyDataModel,
    context: Context
): RecyclerView.Adapter<ApproachAdapter.ApproachHolder> (){
    var approaches = apprachesOfExercise
    val curDataModel = datamodel
    val curContext = context

    class ApproachHolder(
        item: View,
        holdDatamodel:MyDataModel,
        holdContext:Context
    ): RecyclerView.ViewHolder(item){
        var binding = ItemApproachOfTrainBinding.bind(item)
        var curDataModel = holdDatamodel
        var curContext = holdContext

        fun bind(mesuresAndValues:HashMap<String, Float>, position: Int){
            binding.curNumber.text = position.toString()
            var adapter = PartOfApproachAdapter(mesuresAndValues,curDataModel, curContext)
            binding.mesuresAndValues.layoutManager =  LinearLayoutManager(curContext)
            binding.mesuresAndValues.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApproachHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_approach_of_train, parent, false)

        val binding = ItemApproachOfTrainBinding.bind(view)
        return ApproachHolder(
            binding.root,
            curDataModel,
            curContext
        )
    }

    override fun onBindViewHolder(holder: ApproachHolder, position: Int) {
        holder.bind(approaches.get(position), position)
    }

    override fun getItemCount(): Int {
        return approaches.size
    }

}
package com.example.trainer_helper_and_eat_supplements.Adapters.Approach

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.ItemPartOfApproachBinding

class PartOfApproachAdapter(
    dataOfApproach:HashMap<String, Float>,
    datamodel: MyDataModel,
    context: Context
): RecyclerView.Adapter<PartOfApproachAdapter.PartOfApproachHolder>() {
    var curDataOfApproach = dataOfApproach
    val curDataModel = datamodel
    val curContext = context
    var keysArray= arrayListOf<String>()
    init {
        for (key in curDataOfApproach.keys){
            keysArray.add(key)
        }
    }


    class PartOfApproachHolder(item: View,
                               holdDatamodel: MyDataModel,
                               holdContext: Context
    ): RecyclerView.ViewHolder(item){
        var binding = ItemPartOfApproachBinding.bind(item)
        var curDataModel = holdDatamodel
        var curContext = holdContext
        fun bind(key:String, value:Float){
            binding.measureText.text = key
            binding.valueText.text = value.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartOfApproachHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_part_of_approach, parent, false)

        val binding = ItemPartOfApproachBinding.bind(view)
        return PartOfApproachHolder(
            binding.root,
            curDataModel,
            curContext
        )
    }

    override fun onBindViewHolder(holder: PartOfApproachHolder, position: Int) {
        var curKey = keysArray.get(position)
        var myVal = curDataOfApproach.get(curKey)!!
        holder.bind(curKey, myVal)
    }

    override fun getItemCount(): Int {
        return curDataOfApproach.size
    }

}
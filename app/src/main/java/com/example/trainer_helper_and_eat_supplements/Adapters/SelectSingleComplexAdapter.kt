package com.example.trainer_helper_and_eat_supplements.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.*
import com.example.trainer_helper_and_eat_supplements.databinding.ItemSelectComplexForTrainBinding


class SelectSingleComplexAdapter(
    complexesNames:List<String>,
    datamodel: MyDataModel,
    context: Context
): RecyclerView.Adapter<SelectSingleComplexAdapter.SelectSingleComplexHolder>() {
    val allComplexesNames = complexesNames
    val curDataModel = datamodel
    val curContext = context
    // Выбранный элемент(1 из списка)
    var selectedItem = 0
    var nameOfSelectedItem = ""



    class SelectSingleComplexHolder(
        item: View,
        holdDatamodel:MyDataModel,
        holdContext:Context
    ) : RecyclerView.ViewHolder(item){
        var binding = ItemSelectComplexForTrainBinding.bind(item)
        var curDataModel = holdDatamodel
        var curContext = holdContext

        fun bind(str:String, pos:Int){
            binding.radioButton.text = str

            binding.infoBtn.setOnClickListener(){
                val intent = Intent(curContext, ObserverOfComplexActivity::class.java)
                intent.putExtra(CONSTANTS.NAMEOFOBSERVE, str)
                curContext.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectSingleComplexHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_select_complex_for_train, parent, false)

        val binding = ItemSelectComplexForTrainBinding.bind(view)

        return SelectSingleComplexHolder(binding.root, curDataModel, curContext)
    }

    override fun onBindViewHolder(holder: SelectSingleComplexHolder, position: Int) {
        holder.bind(allComplexesNames.get(position), position)
        holder.binding.radioButton.isChecked = position == selectedItem
        if(position == selectedItem){
            nameOfSelectedItem = allComplexesNames.get(position)
        }
        holder.binding.radioButton.setOnCheckedChangeListener(){ compBound, b->
            if(b){
                selectedItem = position
                nameOfSelectedItem = allComplexesNames.get(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return allComplexesNames.size
    }
}
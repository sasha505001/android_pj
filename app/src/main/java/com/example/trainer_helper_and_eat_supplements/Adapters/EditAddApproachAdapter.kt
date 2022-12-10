package com.example.trainer_helper_and_eat_supplements.Adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trainer_helper_and_eat_supplements.MyDataModel
import com.example.trainer_helper_and_eat_supplements.R
import com.example.trainer_helper_and_eat_supplements.databinding.ItemEditAddNewApproachBinding

class EditAddApproachAdapter(
    mesureAndVal:HashMap<String, Float>,
    datamodel: MyDataModel,
    context: Context
): RecyclerView.Adapter<EditAddApproachAdapter.EditAddNewApproachHolder>() {

    // Для всего)
    val curDataModel = datamodel
    val curContext = context


    // Меры - значения(старые значения)
    var mesureValuesList = mesureAndVal

    // Список всех мер подхода(позиция - имя меры)
    var allMesuresOfExercise:MutableList<String> = mutableListOf()

    // Список значений мер(позиция - значение)
    var listOfValuesOfMesures:MutableList<String> = mutableListOf()

    init {
        for((key, curVal) in mesureValuesList){
            allMesuresOfExercise.add(key)
            listOfValuesOfMesures.add(curVal.toString())
        }
    }

    // Мера - значение(строка)
    var mesureValueList = hashMapOf<String, String>()

    init {
        for(( mesure, curVal) in mesureValuesList){
            mesureValueList.put(mesure, curVal.toString())
        }
    }




    class EditAddNewApproachHolder(
        item: View,
        holdDatamodel:MyDataModel,
        holdContext:Context
    ) : RecyclerView.ViewHolder(item){
        var binding = ItemEditAddNewApproachBinding.bind(item)
        var curDataModel = holdDatamodel
        var curContext = holdContext

        fun bind(str:String, pos:Int, mesValue:String){
            binding.headerOfMesure.text = str
            binding.valueOfMesure.setText(mesValue)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditAddNewApproachHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_edit_add_new_approach, parent, false)

        val binding = ItemEditAddNewApproachBinding.bind(view)
        return EditAddNewApproachHolder(
            binding.root,
            curDataModel,
            curContext
        )
    }

    override fun onBindViewHolder(holder: EditAddNewApproachHolder, position: Int) {
        holder.bind(allMesuresOfExercise.get(position), position, listOfValuesOfMesures.get(position))
        holder.binding.valueOfMesure.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listOfValuesOfMesures.set(holder.adapterPosition, p0.toString())
                mesureValueList.put(allMesuresOfExercise.get(holder.adapterPosition), p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun getItemCount(): Int {
        return allMesuresOfExercise.size
    }
}